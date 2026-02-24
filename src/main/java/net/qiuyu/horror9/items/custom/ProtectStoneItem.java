package net.qiuyu.horror9.items.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.qiuyu.horror9.Horror9;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProtectStoneItem extends Item implements ICurioItem {

    public static final String NBT_EFFECTS = "Effects";
    /** 存储上次放置移动光源的方块位置（LongTag 列表，BlockPos.asLong） */
    public static final String NBT_LAST_LIGHT_POSITIONS = "LastLightPositions";
    /** 移动光源效果 ID：1 级 = 玩家位置发光，亮度同火把；2 级 = 一级亮度的三倍距离（3 格半径） */
    public static final ResourceLocation MOVING_LIGHT_1_ID = ResourceLocation.parse(Horror9.MODID + ":moving_light_1");
    public static final ResourceLocation MOVING_LIGHT_2_ID = ResourceLocation.parse(Horror9.MODID + ":moving_light_2");
    /** 火把亮度（原版 14） */
    private static final int TORCH_LIGHT_LEVEL = 14;
    /** 2 级移动光源半径（格） */
    private static final int MOVING_LIGHT_2_RADIUS = 3;
    private static final int MAX_LIGHT_BLOCKS_LEVEL_2 = 50;

    /** 默认效果持续时间（tick），15 秒 */
    private static final int DEFAULT_DURATION_TICKS = 300;
    private static final int DEFAULT_AMPLIFIER = 0;
    /** 每多少 tick 刷新一次效果，避免重复施加 */
    private static final int REFRESH_INTERVAL = 20;
    /** 护石单个效果默认最大等级，用于 tooltip 显示方框数量 */
    private static final int DEFAULT_MAX_LEVEL = 3;
    private static final char FILLED_SQUARE = '■';
    private static final char EMPTY_SQUARE = '□';

    /** 原版正面效果 ID 集合，供 tooltip/校验/后续配置使用；自定义效果为注册表中任意有效 ID */
    public static final Set<ResourceLocation> VANILLA_POSITIVE_EFFECT_IDS = Set.of(
            ResourceLocation.parse("minecraft:regeneration"),
            ResourceLocation.parse("minecraft:movement_speed"),
            ResourceLocation.parse("minecraft:dig_speed"),
            ResourceLocation.parse("minecraft:damage_boost"),
            ResourceLocation.parse("minecraft:instant_health"),
            ResourceLocation.parse("minecraft:absorption"),
            ResourceLocation.parse("minecraft:resistance"),
            ResourceLocation.parse("minecraft:fire_resistance"),
            ResourceLocation.parse("minecraft:water_breathing"),
            ResourceLocation.parse("minecraft:invisibility"),
            ResourceLocation.parse("minecraft:night_vision"),
            ResourceLocation.parse("minecraft:luck"),
            ResourceLocation.parse("minecraft:slow_falling"),
            ResourceLocation.parse("minecraft:dolphins_grace"),
            ResourceLocation.parse("minecraft:conduit_power"),
            ResourceLocation.parse("minecraft:hero_of_the_village")
    );

    public ProtectStoneItem(Properties pProperties) {
        super(pProperties.stacksTo(1));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Level level = slotContext.entity().level();
        if (level.isClientSide()) {
            return;
        }
        if (!(slotContext.entity() instanceof LivingEntity living)) {
            return;
        }
        // 移动光源：每 tick 更新，使光源跟随玩家
        int movingLightLevel = getMovingLightLevel(stack);
        if (movingLightLevel > 0) {
            clearLightAtPositions(level, stack);
            applyMovingLight(level, living.blockPosition(), stack, movingLightLevel);
        }
        // 药水效果：每 REFRESH_INTERVAL tick 刷新
        if (level.getGameTime() % REFRESH_INTERVAL != 0) {
            return;
        }
        RegistryAccess registryAccess = level.registryAccess();
        List<MobEffectInstance> effects = getEffectsFromNbt(stack, registryAccess);
        if (!effects.isEmpty()) {
            applyProtectStoneEffects(living, stack, effects);
        }
    }

    /** 卸下护石时清除已放置的移动光源；由 Curios 在卸下时回调（若 API 支持）。 */
    public void onUnequip(SlotContext slotContext, ItemStack stack) {
        if (!slotContext.entity().level().isClientSide()) {
            clearLightAtPositions(slotContext.entity().level(), stack);
        }
    }

    /** 从 NBT 的 Effects 列表中解析移动光源等级：0=无，1=一级，2=二级 */
    public static int getMovingLightLevel(ItemStack stack) {
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        if (!tag.contains(NBT_EFFECTS, Tag.TAG_LIST)) return 0;
        ListTag list = tag.getList(NBT_EFFECTS, Tag.TAG_STRING);
        boolean has1 = false, has2 = false;
        for (int i = 0; i < list.size(); i++) {
            String id = list.getString(i);
            if (MOVING_LIGHT_2_ID.toString().equals(id)) has2 = true;
            else if (MOVING_LIGHT_1_ID.toString().equals(id)) has1 = true;
        }
        return has2 ? 2 : (has1 ? 1 : 0);
    }

    /** 清除护石在 NBT 中记录的上次放置的光源方块，并清空 NBT 中的记录 */
    public static void clearLightAtPositions(Level level, ItemStack stack) {
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        if (!tag.contains(NBT_LAST_LIGHT_POSITIONS, Tag.TAG_LIST)) return;
        ListTag posList = tag.getList(NBT_LAST_LIGHT_POSITIONS, Tag.TAG_LONG);
        for (int i = 0; i < posList.size(); i++) {
            Tag t = posList.get(i);
            if (!(t instanceof LongTag lt)) continue;
            BlockPos pos = BlockPos.of(lt.getAsLong());
            if (level.getBlockState(pos).is(Blocks.LIGHT)) {
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            }
        }
        tag.remove(NBT_LAST_LIGHT_POSITIONS);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }

    /** 在玩家周围放置移动光源方块并写入 NBT。仅在空中放置，不替换已有方块。 */
    private static void applyMovingLight(Level level, BlockPos center, ItemStack stack, int levelNum) {
        BlockState lightState = Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL, TORCH_LIGHT_LEVEL);
        ListTag posList = new ListTag();
        if (levelNum == 1) {
            BlockPos at = center.above();
            if (level.getBlockState(at).isAir()) {
                level.setBlock(at, lightState, 3);
                posList.add(LongTag.valueOf(at.asLong()));
            }
        } else if (levelNum == 2) {
            int count = 0;
            for (int dx = -MOVING_LIGHT_2_RADIUS; dx <= MOVING_LIGHT_2_RADIUS && count < MAX_LIGHT_BLOCKS_LEVEL_2; dx++) {
                for (int dy = -MOVING_LIGHT_2_RADIUS; dy <= MOVING_LIGHT_2_RADIUS && count < MAX_LIGHT_BLOCKS_LEVEL_2; dy++) {
                    for (int dz = -MOVING_LIGHT_2_RADIUS; dz <= MOVING_LIGHT_2_RADIUS && count < MAX_LIGHT_BLOCKS_LEVEL_2; dz++) {
                        if (dx * dx + dy * dy + dz * dz > MOVING_LIGHT_2_RADIUS * MOVING_LIGHT_2_RADIUS) continue;
                        BlockPos pos = center.offset(dx, dy, dz);
                        if (level.getBlockState(pos).isAir()) {
                            level.setBlock(pos, lightState, 3);
                            posList.add(LongTag.valueOf(pos.asLong()));
                            count++;
                        }
                    }
                }
            }
        }
        if (!posList.isEmpty()) {
            CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
            tag.put(NBT_LAST_LIGHT_POSITIONS, posList);
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        }
    }

    /**
     * 从护石 NBT 解析效果列表。NBT 键 {@link #NBT_EFFECTS} 为 ListTag of String（效果 ID，如 minecraft:regeneration）。
     * 支持原版与任意在注册表中存在的自定义效果。
     */
    public static List<MobEffectInstance> getEffectsFromNbt(ItemStack stack, RegistryAccess registryAccess) {
        List<MobEffectInstance> list = new ArrayList<>();
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        if (!tag.contains(NBT_EFFECTS, Tag.TAG_LIST)) {
            return list;
        }
        ListTag effectsList = tag.getList(NBT_EFFECTS, Tag.TAG_STRING);
        var registry = registryAccess.registry(Registries.MOB_EFFECT);
        if (registry.isEmpty()) {
            return list;
        }
        for (int i = 0; i < effectsList.size(); i++) {
            String idStr = effectsList.getString(i);
            if (MOVING_LIGHT_1_ID.toString().equals(idStr) || MOVING_LIGHT_2_ID.toString().equals(idStr)) {
                continue; // 移动光源非药水效果，由 curioTick 单独处理
            }
            try {
                ResourceLocation id = ResourceLocation.parse(idStr);
                ResourceKey<MobEffect> key = ResourceKey.create(Registries.MOB_EFFECT, id);
                registry.get().getHolder(key).ifPresent(holder ->
                        list.add(new MobEffectInstance(holder, DEFAULT_DURATION_TICKS, DEFAULT_AMPLIFIER)));
            } catch (Exception ignored) {
                // 无效 ID 跳过
            }
        }
        return list;
    }

    /**
     * 装备护石时应用效果的可扩展入口。当前实现：对列表中的每个效果调用 addEffect；
     * 后续可在此扩展时长/等级映射、条件判断等。
     */
    protected void applyProtectStoneEffects(LivingEntity wearer, ItemStack stack, List<MobEffectInstance> effects) {
        for (MobEffectInstance inst : effects) {
            wearer.addEffect(new MobEffectInstance(inst));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag flag) {
        tooltipComponents.add(Component.translatable("tooltip.horror9.protect_stone.line1").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.translatable("tooltip.horror9.protect_stone.line2").withStyle(ChatFormatting.GRAY));

        appendEffectDetailsTooltip(stack, tooltipComponents);

        super.appendHoverText(stack, context, tooltipComponents, flag);
    }

    /**
     * 根据护石 NBT 中的效果列表生成详细 tooltip：展示效果名称与等级方框。
     * 等级采用“实心方框 + 空心方框”的形式展示当前等级与上限等级。
     */
    private static void appendEffectDetailsTooltip(ItemStack stack, List<Component> tooltipComponents) {
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        if (!tag.contains(NBT_EFFECTS, Tag.TAG_LIST)) {
            return;
        }

        ListTag effectsList = tag.getList(NBT_EFFECTS, Tag.TAG_STRING);
        if (effectsList.isEmpty()) {
            return;
        }

        Map<String, Integer> effectCountMap = new LinkedHashMap<>();
        for (int i = 0; i < effectsList.size(); i++) {
            String idStr = effectsList.getString(i);
            effectCountMap.merge(idStr, 1, Integer::sum);
        }

        if (effectCountMap.isEmpty()) {
            return;
        }

        tooltipComponents.add(Component.empty());
        tooltipComponents.add(Component.translatable("tooltip.horror9.protect_stone.effects").withStyle(ChatFormatting.GRAY));

        for (Map.Entry<String, Integer> entry : effectCountMap.entrySet()) {
            String idStr = entry.getKey();
            int level = Math.max(1, entry.getValue());

            ResourceLocation id;
            try {
                id = ResourceLocation.parse(idStr);
            } catch (Exception e) {
                tooltipComponents.add(Component.literal(idStr));
                continue;
            }

            String translationKey = "effect." + id.getNamespace() + "." + id.getPath();
            Component nameComponent = Component.translatable(translationKey);

            int maxLevel = DEFAULT_MAX_LEVEL;
            int clampedLevel = Math.min(level, maxLevel);
            String levelBar = buildLevelBar(clampedLevel, maxLevel);

            ChatFormatting nameColor = VANILLA_POSITIVE_EFFECT_IDS.contains(id) || Horror9.MODID.equals(id.getNamespace())
                    ? ChatFormatting.DARK_GREEN
                    : ChatFormatting.DARK_RED;

            tooltipComponents.add(
                    Component.literal(" ")
                            .append(nameComponent.copy().withStyle(nameColor))
                            .append(" ")
                            .append(Component.literal(levelBar).withStyle(ChatFormatting.GOLD))
            );
        }
    }

    private static String buildLevelBar(int currentLevel, int maxLevel) {
        StringBuilder sb = new StringBuilder(maxLevel);
        for (int i = 0; i < maxLevel; i++) {
            sb.append(i < currentLevel ? FILLED_SQUARE : EMPTY_SQUARE);
        }
        return sb.toString();
    }
}

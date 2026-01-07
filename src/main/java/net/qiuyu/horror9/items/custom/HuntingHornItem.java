package net.qiuyu.horror9.items.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.qiuyu.horror9.Horror9;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class HuntingHornItem extends Item implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    /**
     * 技能类型
     */
    public enum SkillType {
        LEFT_CLICK,
        PERFORMANCE_END
    }

    /**
     * 技能定义
     */
    public record HornSkill(String id, BiConsumer<Player, Level> effect, SkillType type) {}

    /**
     * 旋律 Map：Key 为音符序列，Value 为技能
     */
    private static final Map<List<HornType>, HornSkill> MELODIES = new HashMap<>();
    private static final Map<String, HornSkill> SKILLS_BY_ID = new HashMap<>();

    static {
        // 注册旋律 {2, 1, 3} -> {BLUE, RED, GREEN}
        // 效果：给所有附近20格内的玩家添加持续60秒的伤害吸收,伤害吸收的量为5颗心
        registerSkill(List.of(HornType.BLUE, HornType.RED, HornType.GREEN),
                new HornSkill("absorption", (player, level) -> {
                    List<Player> nearbyPlayers = level.getEntitiesOfClass(Player.class, player.getBoundingBox().inflate(20.0D));
                    for (Player target : nearbyPlayers) {
                        target.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 1200, 2));
                    }
                }, SkillType.PERFORMANCE_END));

        // 示例：双红触发一个左键技能（增加移动速度）
        registerSkill(List.of(HornType.RED, HornType.RED),
                new HornSkill("speed_boost", (player, level) -> player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0)), SkillType.LEFT_CLICK));
    }

    private static void registerSkill(List<HornType> sequence, HornSkill skill) {
        MELODIES.put(sequence, skill);
        SKILLS_BY_ID.put(skill.id(), skill);
    }

    public HuntingHornItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    /**
     * 必须重写此方法并返回一个非零值，右键才能“按住”
     * 72000 tick = 1小时，足够长了
     */
    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    /**
     * 定义右键按住时的动作，比如 BOW(拉弓), SPEAR(投矛), BLOCK(格挡) 或 NONE
     */
    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    /**
     * 处理右键交互
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);

        // 能够读取到上文设置好的对应按键
        if (pLevel.isClientSide) {
            // 通过 Proxy 安全读取 ModKeyBindings 中注册的按键状态
            if (Horror9.PROXY.isNoteKey2Down()) {
                // 逻辑已由全局事件监听器 HuntingHornClientEvents 处理发包
                // 此处保留作为按键读取示例
            }
        }

        // 普通右键：开始“使用”物品（进入按住状态），这对检测“左键+右键”至关重要
        pPlayer.startUsingItem(pUsedHand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if (entity instanceof Player player) {
            Level level = player.level();

            if (!level.isClientSide) {
                // 触发存储在技能队列中的左键技能
                CompoundTag nbt = stack.getTag();
                if (nbt != null && nbt.contains("LCSkill")) {
                    String skillId = nbt.getString("LCSkill");
                    HornSkill skill = SKILLS_BY_ID.get(skillId);
                    if (skill != null) {
                        skill.effect().accept(player, level);
                        nbt.remove("LCSkill");
                        player.displayClientMessage(Component.literal("§b左键技能已触发!"), true);
                    }
                }
            }
            
            if (level.isClientSide) {
                // 判定：左键 + 右键 (通过读取 ModKeyBindings 中的 Ctrl + 中键 映射)
                if (Horror9.PROXY.isNoteKey3Down() && player.isUsingItem()) {
                    return true;
                }

                // 检查是否按下设置好的 Ctrl + 左键
                if (Horror9.PROXY.isNoteKey1Down()) {
                    return true;
                }
            }
        }
        return super.onEntitySwing(stack, entity);
    }

    /**
     * 添加音符的具体实现，可由网络消息或本地调用
     */
    public void addNote(ItemStack stack, Player player, int noteType) {
        if (player.level().isClientSide) return;

        HornType type = HornType.values()[noteType % HornType.values().length];
        playSeqAdd(stack, player, type);
    }

    private void playSeqAdd(ItemStack stack, Player player, HornType type){
        CompoundTag nbt = stack.getOrCreateTag();
        ListTag list = nbt.getList("InputSeq", Tag.TAG_STRING);
        list.add(StringTag.valueOf(type.name()));
        
        // 保持序列长度，最大长度为 5
        if (list.size() > 5) {
            list.remove(0);
        }
        
        nbt.put("InputSeq", list);
        
        // 发送动作条提示
        player.displayClientMessage(Component.literal("§6输入音符: §f" + type.name()), true);
        
        matchAndAddSkill(stack, player);
    }

    private void matchAndAddSkill(ItemStack stack, Player player){
        CompoundTag nbt = stack.getOrCreateTag();
        ListTag list = nbt.getList("InputSeq", Tag.TAG_STRING);
        if (list.isEmpty()) return;

        List<HornType> currentSeq = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            try {
                currentSeq.add(HornType.valueOf(list.getString(i)));
            } catch (IllegalArgumentException e) {
                // 忽略无效音符
            }
        }

        // 优先匹配最长的符合规则
        HornSkill longestMatch = null;
        int maxLen = -1;

        for (Map.Entry<List<HornType>, HornSkill> entry : MELODIES.entrySet()) {
            List<HornType> melody = entry.getKey();
            if (currentSeq.size() >= melody.size()) {
                // 检查当前序列的结尾是否匹配该旋律
                List<HornType> subSeq = currentSeq.subList(currentSeq.size() - melody.size(), currentSeq.size());
                if (subSeq.equals(melody)) {
                    if (melody.size() > maxLen) {
                        maxLen = melody.size();
                        longestMatch = entry.getValue();
                    }
                }
            }
        }

        if (longestMatch != null) {
            addSkillToQueue(nbt, longestMatch, player);
            // 这里可以根据需要决定是否在匹配成功后清空 InputSeq
            // 按照通常设计，存入技能队列后，输入序列会清空，以便开始下一次积攒
        }
    }

    private void addSkillToQueue(CompoundTag nbt, HornSkill skill, Player player) {
        if (skill.type() == SkillType.LEFT_CLICK) {
            // 最多存储 1 种由鼠标左键触发的效果
            nbt.putString("LCSkill", skill.id());
            player.displayClientMessage(Component.literal("§b获得左键技能: §f" + skill.id()), true);
        } else if (skill.type() == SkillType.PERFORMANCE_END) {
            // 最多存储 3 种演奏结束后触发的效果
            ListTag peSkills = nbt.getList("PESkills", Tag.TAG_STRING);
            peSkills.add(StringTag.valueOf(skill.id()));
            if (peSkills.size() > 3) {
                peSkills.remove(0);
            }
            nbt.put("PESkills", peSkills);
            player.displayClientMessage(Component.literal("§a获得演奏技能: §f" + skill.id()), true);
        }
    }

    private void startPlaying(ItemStack stack, Player player){
        CompoundTag nbt = stack.getOrCreateTag();
        ListTag peSkills = nbt.getList("PESkills", Tag.TAG_STRING);
        if (peSkills.isEmpty()) {
            player.displayClientMessage(Component.literal("§c技能队列中没有可演奏的技能!"), true);
            return;
        }

        for (int i = 0; i < peSkills.size(); i++) {
            String skillId = peSkills.getString(i);
            HornSkill skill = SKILLS_BY_ID.get(skillId);
            if (skill != null) {
                skill.effect().accept(player, player.level());
            }
        }
        
        nbt.remove("PESkills");
        player.displayClientMessage(Component.literal("§a演奏结束，技能效果已生效!"), true);
    }

    private void playSeqRemove(ItemStack stack){
        stack.getOrCreateTag().remove("InputSeq");
    }

    public enum HornType{
        RED,
        GREEN,
        BLUE
    }
}

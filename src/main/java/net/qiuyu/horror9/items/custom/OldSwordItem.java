package net.qiuyu.horror9.items.custom;

import com.google.common.collect.Sets;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OldSwordItem extends SwordItem {
    public static final Set<ItemAbility> OLD_SWORD_ACTIONS = of(net.neoforged.neoforge.common.ItemAbilities.SWORD_DIG);
    private static Set<ItemAbility> of(ItemAbility... actions) {
        return Stream.of(actions).collect(Collectors.toCollection(Sets::newIdentityHashSet));
    }
    private final int attackDamage;
    private static final int USE_DURATION = 72000;
    public static final float DAMAGE_REDUCTION = 0.5f;

    public OldSwordItem(Tier tier, Item.Properties properties, float attackDamage) {
        super(tier, properties.component(DataComponents.TOOL, createToolProperties()));
        this.attackDamage = (int)(1.0 + attackDamage + tier.getAttackDamageBonus());
    }

    public static ItemAttributeModifiers createAttributes(Tier tier, int attackDamage) {
        return createAttributes(tier, (float)attackDamage);
    }

    public static ItemAttributeModifiers createAttributes(Tier tier, float attackDamage) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(
                                BASE_ATTACK_DAMAGE_ID, (double)((float)attackDamage + tier.getAttackDamageBonus()), AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(   // 加快攻速，快到攻击冷却指示器不会出现，这是个简单处理，除非有锁攻速的饰品，不然这个够用了
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID, (double)100, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .build().withTooltip(false);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);

        // 开始使用格挡
        player.startUsingItem(usedHand);
        // TODO: 渲染第一人称格挡动画

        return InteractionResultHolder.consume(itemstack);
    }

    public static boolean isBlockingWithSword(Player player) {
        ItemStack usedItem = player.getUseItem();

        // 检查是否正在使用物品且物品是当前剑
        return player.isUsingItem() && usedItem.getItem() instanceof OldSwordItem;
    }

    public static boolean canBlockAttack(DamageSource source) {
        Holder<DamageType> damageType = source.typeHolder();

        // 检查是否是投射物（远程伤害）
        if (damageType.is(DamageTypeTags.IS_PROJECTILE)) {
            return true;
        }

        // 检查是否是近战伤害（有实体直接攻击）
        Entity directEntity = source.getDirectEntity();
        if (directEntity instanceof LivingEntity) {
            return true;
        }

        // 检查是否有其他实体造成伤害（间接近战）
        Entity causingEntity = source.getEntity();
        if (causingEntity instanceof LivingEntity) {
            return true;
        }

        return false;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BLOCK; // 使用格挡动画
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return USE_DURATION;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.horror9.old_sword.line1").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.empty());
        tooltipComponents.add(Component.translatable("tooltip.horror9.when_in_main_hand").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.literal(" ")
                .append(String.valueOf(attackDamage))
                .append(Component.literal(" "))
                .append(Component.translatable("tooltip.horror9.old_sword.line2"))
                .withStyle(ChatFormatting.DARK_GREEN));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ItemAbility itemAbility) {
        return OLD_SWORD_ACTIONS.contains(itemAbility);
    }
}

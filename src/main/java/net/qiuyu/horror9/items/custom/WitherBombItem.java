package net.qiuyu.horror9.items.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.qiuyu.horror9.entity.ModEntityTypes;
import net.qiuyu.horror9.entity.custom.WitherBombEntity;
import net.qiuyu.horror9.items.renderer.OxygenDestroyerRenderer;
import net.qiuyu.horror9.items.renderer.WitherBombRenderer;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

public class WitherBombItem extends Item implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final int USE_TIME = 20;
    private static final int[] CHARGE_POINTS = {5, 10, 15, 20};

    public WitherBombItem(Properties properties) {
        super(properties);
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseTicks) {
        if (level.isClientSide && entity instanceof Player player) {
            int usedTicks = this.getUseDuration(stack) - remainingUseTicks;

            // 检查是否到达蓄力节点
            for (int chargePoint : CHARGE_POINTS) {
                if (usedTicks == chargePoint) {
                    // 播放蓄力音效
                    level.playSound(
                            player,
                            player.getX(),
                            player.getY(),
                            player.getZ(),
                            SoundEvents.EXPERIENCE_ORB_PICKUP,
                            SoundSource.PLAYERS,
                            0.7F,
                            1.0F + (chargePoint - 5) * 0.05F // 音调逐渐升高
                    );
                    break;
                }
            }
        }

        super.onUseTick(level, entity, stack, remainingUseTicks);
    }

    @Override
    public void releaseUsing(ItemStack itemstack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player) {
            int i = this.getUseDuration(itemstack) - timeLeft;
            if (i < USE_TIME) {
                return;
            }
            // 创建实体
            if (!level.isClientSide()) {
                WitherBombEntity witherBombEntity = new WitherBombEntity(ModEntityTypes.WITHER_BOMB.get(),
                        player.position().x(), player.getEyePosition().y, player.position().z(), 0F, -0.5F, 0F, level);
                witherBombEntity.setOwner(player);
                witherBombEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                level.addFreshEntity(witherBombEntity);
            }
            // 播放音效
            level.playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    SoundEvents.EGG_THROW,
                    SoundSource.NEUTRAL,
                    0.5F,
                    0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)
            );

            // 减少物品数量
            if (!player.isCreative()){
                itemstack.shrink(1);
            }
        }
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW; // 使用拉弓动画
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);

        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.horror9.wither_bomb.line1").withStyle(ChatFormatting.RED));
        pTooltipComponents.add(Component.translatable("tooltip.horror9.wither_bomb.line2").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private WitherBombRenderer renderer;
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null){
                    this.renderer = new WitherBombRenderer();
                }
                return this.renderer;
            }
        });
    }

}

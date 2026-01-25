package net.qiuyu.horror9.items.custom;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.qiuyu.horror9.entity.ModEntityTypes;
import net.qiuyu.horror9.entity.custom.WitherBombEntity;
import net.qiuyu.horror9.items.renderer.WitherBombRenderer;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class WitherBombItem extends Item implements GeoItem {
    private static final int USE_TIME = 20;
    private static final int[] CHARGE_POINTS = {5, 10, 15, 20};
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public WitherBombItem(Properties properties) {
        super(properties);
    }

    @Override
    public void onUseTick(Level level, @NotNull LivingEntity entity, @NotNull ItemStack stack, int remainingUseTicks) {
        if (level.isClientSide && entity instanceof Player player) {
            int usedTicks = this.getUseDuration(stack, entity) - remainingUseTicks;

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
    public void releaseUsing(@NotNull ItemStack itemstack, @NotNull Level level, @NotNull LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player) {
            int i = this.getUseDuration(itemstack, entityLiving) - timeLeft;
            if (i < USE_TIME) {
                return;
            }
            // 创建实体
            if (!level.isClientSide()) {
                WitherBombEntity witherBombEntity = new WitherBombEntity(level, player);
                witherBombEntity.setItem(itemstack);
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
    public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
        return 72000;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BOW; // 使用拉弓动画
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);

        return InteractionResultHolder.consume(itemstack);
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
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

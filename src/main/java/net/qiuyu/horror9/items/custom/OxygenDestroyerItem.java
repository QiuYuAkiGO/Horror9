package net.qiuyu.horror9.items.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.qiuyu.horror9.items.renderer.OxygenDestroyerRenderer;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

public class OxygenDestroyerItem extends AxeItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public OxygenDestroyerItem(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Item.Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    public static Item.Properties createDefaultProperties() {
        return new Item.Properties().durability(3750);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        if (pPlayer.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(itemstack);
        }
        pPlayer.playSound(SoundEvents.NOTE_BLOCK_BELL.get(), 1.0F, 1.0F);
        pPlayer.startUsingItem(pUsedHand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        int i = this.getUseDuration(pStack) - pRemainingUseDuration;
        if (i == 40) {
            pLivingEntity.playSound(SoundEvents.NOTE_BLOCK_BELL.get(), 1.0F, 2.0F);
        }
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeLeft) {
        if (pLivingEntity instanceof Player player) {
            int i = this.getUseDuration(pStack) - pTimeLeft;
            if (i >= 40) { // 2 seconds
                player.playSound(SoundEvents.GENERIC_EXPLODE, 1.0F, 1.0F);
                if (!pLevel.isClientSide) {
                    performAoeAttack(player, pLevel);
                    player.getCooldowns().addCooldown(this, 240); // 12 seconds
                }
            }
        }
    }

    private void performAoeAttack(Player player, Level level) {
        if (!(level instanceof ServerLevel serverLevel)) return;
        double range = 3.0;
        Vec3 lookVec = player.getLookAngle();
        Vec3 playerPos = player.getEyePosition();
        AABB area = player.getBoundingBox().inflate(range);

        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area, (entity) -> entity != player && entity.isAlive());

        for (LivingEntity target : entities) {
            Vec3 targetVec = target.position().add(0, target.getBbHeight() / 2.0, 0).subtract(playerPos);
            double distance = targetVec.length();

            if (distance <= range) {
                targetVec = targetVec.normalize();
                double dotProduct = lookVec.dot(targetVec);
                // cos(120/2) = cos(60) = 0.5
                if (dotProduct >= 0.5) {
                    target.hurt(level.damageSources().playerAttack(player), 16.0F);
                    serverLevel.sendParticles(ParticleTypes.EXPLOSION, target.getX(), target.getY() + target.getBbHeight() / 2.0, target.getZ(), 5, 0.2, 0.2, 0.2, 0.0);
                    if (!(target instanceof Player tp && tp.isCreative())) {
                        target.setDeltaMovement(target.getDeltaMovement().x, 0.8, target.getDeltaMovement().z);
                        target.hurtMarked = true;
                    }
                }
            }
        }

        // 地面方块动画效果
        BlockPos centerPos = player.blockPosition();
        for (int x = -4; x <= 4; x++) {
            for (int z = -4; z <= 4; z++) {
                for (int y = 2; y >= -2; y--) {
                    BlockPos pos = centerPos.offset(x, y, z);
                    BlockState state = level.getBlockState(pos);
                    if (state.isAir()) continue;

                    // 判定是否为地表方块
                    if (!level.getBlockState(pos.above()).isAir()) continue;

                    Vec3 blockCenter = Vec3.atCenterOf(pos);
                    Vec3 toBlock = blockCenter.subtract(playerPos);
                    double distance = toBlock.length();

                    if (distance <= range + 0.5) {
                        double dotProduct = lookVec.dot(toBlock.normalize());
                        if (dotProduct >= 0.5) {
                            int delay = (int) (distance * 3);
                            scheduleDelayTask(serverLevel.getServer(), delay, () -> {
                                if (level.getBlockState(pos).equals(state)) {
                                    serverLevel.sendParticles(ParticleTypes.EXPLOSION, blockCenter.x, blockCenter.y + 0.5, blockCenter.z, 2, 0.1, 0.1, 0.1, 0.0);
                                    // 使用 fall 产生实体，然后立即恢复原方块以达到“只生成实体”的效果
                                    FallingBlockEntity fallingBlock = FallingBlockEntity.fall(level, pos, state);
                                    level.setBlock(pos, state, 3); // 立即恢复方块

                                    fallingBlock.time = 1; // 设置为1以跳过初始删除方块的逻辑
                                    fallingBlock.dropItem = false;
                                    fallingBlock.setDeltaMovement(0, 0.3, 0);
                                    fallingBlock.hurtMarked = true;

                                    // 20tick后自动消失，防止实体下落后尝试替换方块
                                    scheduleDelayTask(serverLevel.getServer(), 20, fallingBlock::discard);
                                }
                            });
                            break;
                        }
                    }
                }
            }
        }
    }

    private void scheduleDelayTask(MinecraftServer server, int delay, Runnable task) {
        if (delay <= 0) {
            server.execute(task);
        } else {
            server.execute(() -> scheduleDelayTask(server, delay - 1, task));
        }
    }



    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.horror9.oxygen_destroyer.line1").withStyle(ChatFormatting.LIGHT_PURPLE));
        pTooltipComponents.add(Component.translatable("tooltip.horror9.oxygen_destroyer.line2").withStyle(ChatFormatting.DARK_RED).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private OxygenDestroyerRenderer renderer;
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null){
                    this.renderer = new OxygenDestroyerRenderer();
                }
                return this.renderer;
            }
        });
    }
}

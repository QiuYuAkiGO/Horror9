package net.qiuyu.horror9.entity.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.qiuyu.horror9.entity.ModEntityTypes;
import net.qiuyu.horror9.register.ModItems;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class WitherBombEntity extends ThrowableItemProjectile implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final float RADIUS = 5.0F;

    public WitherBombEntity(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public WitherBombEntity(Level level, LivingEntity owner) {
        super(ModEntityTypes.WITHER_BOMB.get(), owner, level);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ModItems.WITHER_BOMB.get();
    }


    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            // 爆炸
            this.explode(result.getLocation());
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            Vec3i vec3i = result.getDirection().getNormal();
            Vec3 vec3 = Vec3.atLowerCornerOf(vec3i).multiply(0.25, 0.25, 0.25);
            Vec3 vec31 = result.getLocation().add(vec3);
            this.explode(vec31);
            this.discard();
        }
    }

    /**
     * 当此实体击中方块或实体时调用。
     */
    @Override
    protected void onHit(@NotNull HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    protected void explode(Vec3 pos) {
        this.level()
                .explode(
                        this,
                        pos.x(),
                        pos.y(),
                        pos.z(),
                        RADIUS,
                        false,
                        Level.ExplosionInteraction.NONE
                );
        if (this.level() instanceof ServerLevel serverLevel) {
            // 额外生成一些烟雾粒子，增强视觉覆盖范围以匹配伤害半径
            serverLevel.sendParticles(ParticleTypes.EXPLOSION, pos.x(), pos.y(), pos.z(), 2, 1.0, 1.0, 1.0, 0.0);
            serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, pos.x(), pos.y(), pos.z(), 40, RADIUS * 0.5, RADIUS * 0.5, RADIUS * 0.5, 0.1);
        }
        applySplash(pos, new MobEffectInstance(MobEffects.WITHER, 600));
    }

    private void applySplash(Vec3 pos, MobEffectInstance effectInstances) {
        // 效果范围
        AABB aabb = new AABB(pos, pos).inflate(RADIUS);
        // 获取范围内的生物
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, aabb);
        if (list.isEmpty()) {
            return;
        }
        Entity entity = this.getEffectSource();
        for(LivingEntity livingentity : list) {
            // 检查距离，确保是球形范围
            if (livingentity.distanceToSqr(pos) > RADIUS * RADIUS) {
                continue;
            }
            // 判断是否免疫药水
            if (!livingentity.isAffectedByPotions()) {
                continue;
            }
            // 添加效果
            MobEffect mobeffect = effectInstances.getEffect().value();
            if (mobeffect.isInstantenous()) {
                mobeffect.applyInstantenousEffect(this, this.getOwner(), livingentity, effectInstances.getAmplifier(), 1);
            } else {
                int i = effectInstances.mapDuration((duration) -> (int)((double)duration + 0.5D));
                MobEffectInstance mobeffectinstance1 = new MobEffectInstance(effectInstances.getEffect(), i, effectInstances.getAmplifier(), effectInstances.isAmbient(), effectInstances.isVisible());
                if (!mobeffectinstance1.endsWithin(20)) {
                    livingentity.addEffect(mobeffectinstance1, entity);
                }
            }
        }
    }


    @Override
    public void tick() {
        if (!this.level().isClientSide && this.getBlockY() > this.level().getMaxBuildHeight() + 30) {
            this.explode(this.position());
            this.discard();
        } else {
            super.tick();
        }
    }

    /**
     * 当实体受到攻击时调用。
     */
    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        return false;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}

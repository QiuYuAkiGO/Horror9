package net.qiuyu.horror9.entity.custom;

import net.minecraft.core.Vec3i;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

import java.util.List;

public class WitherBombEntity extends AbstractHurtingProjectile implements ItemSupplier {
    ExplosionDamageCalculator explosionDamageCalculator = new ExplosionDamageCalculator();
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(WitherBombEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final float RADIUS = 2.0F;

    public WitherBombEntity(EntityType<? extends WitherBombEntity> entityType, Level level) {
        super(entityType, level);
    }

    public WitherBombEntity(EntityType<? extends WitherBombEntity> entityType, double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ, Level pLevel) {
        super(entityType, pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }

    /**
     * 当击中某个实体时调用
     */
    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            // 爆炸
            this.explode(this.position());
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
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
    protected void onHit(HitResult result) {
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
        applySplash(new MobEffectInstance(MobEffects.WITHER, 600));
    }

    private void applySplash(MobEffectInstance effectInstances) {
        // 效果范围
        AABB aabb = this.getBoundingBox().inflate(5.0D, 5.0D, 5.0D);
        // 获取范围内的生物
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, aabb);
        if (list.isEmpty()) {
            return;
        }
        Entity entity = this.getEffectSource();
        for(LivingEntity livingentity : list) {
            // 判断是否免疫药水
            if (!livingentity.isAffectedByPotions()) {
                return;
            }
            // 添加效果
            MobEffect mobeffect = effectInstances.getEffect();
            if (mobeffect.isInstantenous()) {
                mobeffect.applyInstantenousEffect(this, this.getOwner(), livingentity, effectInstances.getAmplifier(), 1);
            } else {
                int i = effectInstances.mapDuration((duration) -> {
                    return (int)((double)duration + 0.5D);
                });
                MobEffectInstance mobeffectinstance1 = new MobEffectInstance(mobeffect, i, effectInstances.getAmplifier(), effectInstances.isAmbient(), effectInstances.isVisible());
                if (!mobeffectinstance1.endsWithin(20)) {
                    livingentity.addEffect(mobeffectinstance1, entity);
                }
            }

        }
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    public ItemStack getItem() {
        return ItemStack.EMPTY;
    }

    @Override
    protected float getInertia() {
        return 1.0F;
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
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
    }

}

package net.qiuyu.horror9.entity.custom;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class VillageCoreEntity extends Mob {

    public VillageCoreEntity(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    // 实体属性
    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 500.0)
                .build();
    }

    @Override
    public void checkDespawn() {
        // 防止自然消失
        this.noActionTime = 0;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false; // 不会因远离玩家而消失
    }

    @Override
    protected @NotNull AABB makeBoundingBox() {
        double size = 5.0;
        double halfSize = size / 2.0;
        return new AABB(-halfSize, 0, -halfSize, halfSize, size, halfSize)
                .move(this.position());
    }

    @Override
    public boolean isPushable() {
        return false; // 不能被推动
    }

    @Override
    public boolean canBeCollidedWith() {
        return true; // 可以被玩家碰撞
    }

    @Override
    public boolean isNoGravity() {
        return true; // 如果悬空，需要关闭重力
    }

    @Override
    public void move(MoverType type, Vec3 pos) {
        // 覆盖移动方法，完全禁止移动
        super.move(type, Vec3.ZERO);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected void playHurtSound(DamageSource pSource) {
        this.playSound(SoundEvents.IRON_GOLEM_HURT, 1.0F, 3.0F);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
    }

}

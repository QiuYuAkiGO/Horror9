package net.qiuyu.horror9.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.message.BiterDismountMsg;
import net.qiuyu.horror9.message.BiterMountPlayerMsg;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BiterEntity extends Monster implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public BiterEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    // 设置实体基础属性
    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 12.0D) // 最大生命值
                .add(Attributes.ATTACK_DAMAGE, 4.0f) // 攻击力
                .add(Attributes.ATTACK_SPEED, 1.0f) // 攻击速度
                .add(Attributes.MOVEMENT_SPEED, 0.3f) // 移动速度
                .add(Attributes.ATTACK_KNOCKBACK, 0.0f) // 击退力
                .build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(BiterEntity.class, ZombifiedPiglin.class, Zombie.class, AbstractSkeleton.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
//        pPlayer.addEffect(new MobEffectInstance(MobEffects.POISON,
//                pPlayer.hasEffect(MobEffects.POISON) ? pPlayer.getEffect(MobEffects.POISON).getDuration() + 100 : 100, 0));
//        pPlayer.addEffect(new MobEffectInstance(MobEffects.CONFUSION,
//                pPlayer.hasEffect(MobEffects.CONFUSION) ? pPlayer.getEffect(MobEffects.CONFUSION).getDuration() + 100 : 100, 0));
//        pPlayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,
//                pPlayer.hasEffect(MobEffects.BLINDNESS) ? pPlayer.getEffect(MobEffects.BLINDNESS).getDuration() + 100 : 100, 0));
//        pPlayer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
//                pPlayer.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) ? pPlayer.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getDuration() + 100 : 100, 1));
        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    public boolean canRiderInteract() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (this.isPassenger() && pSource.getEntity() != null && pSource.getEntity() == this.getVehicle()) {
            return false;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        boolean flag = super.doHurtTarget(pEntity);
        if (flag && pEntity instanceof LivingEntity && !this.isPassenger()) {
            this.startRiding(pEntity);
            this.playSound(SoundEvents.VEX_CHARGE);
            this.stopTriggeredAnim("attackController",null);
            if (!pEntity.level().isClientSide) {
                Horror9.sendMSGToAll(new BiterMountPlayerMsg(this.getId(), pEntity.getId()));
            }
        }
        return flag;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void rideTick() {
        Entity vehicle = this.getVehicle();
        if (this.isPassenger() && vehicle instanceof LivingEntity mount) {
            this.setDeltaMovement(0, 0, 0);

            super.rideTick();

            this.yBodyRot = mount.yBodyRot;
            this.setYRot(-mount.getYRot());
            this.yHeadRot = mount.yHeadRot;
            this.yRotO = mount.yHeadRot;
            float angle = mount.getYRot() * Mth.DEG_TO_RAD;
            float radius = 0.5F; // 紧贴距离

            if (!this.level().isClientSide && this.isAlive()) {
                mount.hurt(this.damageSources().mobAttack(this), 6.0F);
                mount.addEffect(new MobEffectInstance(MobEffects.POISON, 40, 0));
                mount.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100, 0));
            }

            double extraX = radius * Mth.sin(Mth.PI + angle);
            double extraZ = radius * Mth.cos(angle);
            double targetX = mount.getX() + extraX;
            double targetY = Math.max(mount.getY() + mount.getEyeHeight() * -0.5F, mount.getY());
            double targetZ = mount.getZ() + extraZ;

            if (!this.level().isClientSide) {
                this.setPos(targetX, targetY, targetZ);
            }

            if (!mount.isAlive() || (mount instanceof Player player && player.isCreative())) {
                this.removeVehicle();
                this.stopTriggeredAnim("attackController",null);
                if (!this.level().isClientSide) {
                    Horror9.sendMSGToAll(new BiterDismountMsg(this.getId(), vehicle.getId()));
                }
            }
        }
    }

    // --- GeckoLib 动画处理 ---

    private PlayState predicate(final AnimationState<BiterEntity> event) {
        if (event.isMoving()) {
            // 走路动画名，需与json一致
            event.setAnimation(RawAnimation.begin().thenLoop("walk"));
        } else {
            // 待机动画名
            event.setAnimation(RawAnimation.begin().thenLoop("standby"));
        }
        return PlayState.CONTINUE;
    }

    private PlayState attackPredicate(AnimationState<BiterEntity> event) {
        if (this.swinging) {
            if (this.isPassenger()) {
                event.resetCurrentAnimation();
                event.setAnimation(RawAnimation.begin().then("attack.2", Animation.LoopType.PLAY_ONCE));
            }
            this.swinging = false;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
        controllers.add(new AnimationController<>(this, "attackController", 0, this::attackPredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
        this.playSound(SoundEvents.ZOMBIE_STEP, 1.0F, 2.0F);
    }

    @Override
    protected void playHurtSound(DamageSource pSource) {
        this.playSound(SoundEvents.ZOMBIE_HURT, 1.0F, 3.0F);
    }

    @Override
    public void playAmbientSound() {
        this.playSound(SoundEvents.ZOMBIE_AMBIENT, 1.0F, 3.0F);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.VEX_DEATH;
    }
}

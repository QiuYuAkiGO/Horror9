package net.qiuyu.horror9.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ArthroeeEntity extends Monster implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ArthroeeEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new ArthroeeMoveControl(this);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level pLevel) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, pLevel);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.25f)
                .add(Attributes.ATTACK_KNOCKBACK, 0.2f)
                .add(Attributes.FLYING_SPEED, 0.25f)
                .build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(3, new ArthroeeRandomFlyGoal(this));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void travel(@NotNull Vec3 pTravelVector) {
        if (this.isEffectiveAi() || this.isControlledByLocalInstance()) {
            if (this.isInWater()) {
                this.moveRelative(0.02F, pTravelVector);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.8F));
            } else if (this.isInLava()) {
                this.moveRelative(0.02F, pTravelVector);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.5F));
            } else {
                float f = 0.91F;
                if (this.onGround()) {
                    f = this.level().getBlockState(this.getBlockPosBelowThatAffectsMyMovement()).getBlock().getFriction() * 0.91F;
                }

                float f1 = 0.16277136F / (f * f * f);
                f = 0.91F;
                if (this.onGround()) {
                    f = this.level().getBlockState(this.getBlockPosBelowThatAffectsMyMovement()).getBlock().getFriction() * 0.91F;
                }

                this.moveRelative(this.onGround() ? 0.1F * f1 : 0.02F, pTravelVector);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(f));
            }
        }

        this.calculateEntityAnimation(false);
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, @NotNull DamageSource pSource) {
        return false;
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, @NotNull BlockState pState, @NotNull BlockPos pPos) {
    }

    static class ArthroeeMoveControl extends MoveControl {
        private final ArthroeeEntity arthroee;
        private int floatDuration;

        public ArthroeeMoveControl(ArthroeeEntity arthroee) {
            super(arthroee);
            this.arthroee = arthroee;
        }

        @Override
        public void tick() {
            if (this.operation == Operation.MOVE_TO) {
                if (this.floatDuration-- <= 0) {
                    this.floatDuration += this.arthroee.getRandom().nextInt(5) + 2;
                    Vec3 vec3 = new Vec3(this.wantedX - this.arthroee.getX(), this.wantedY - this.arthroee.getY(), this.wantedZ - this.arthroee.getZ());
                    double d0 = vec3.length();
                    vec3 = vec3.normalize();
                    if (this.canReach(vec3, Mth.ceil(d0))) {
                        this.arthroee.setDeltaMovement(this.arthroee.getDeltaMovement().add(vec3.scale(0.1D)));
                    } else {
                        this.operation = Operation.WAIT;
                    }
                }
            }

            // Keep it 1 block above ground
            double groundY = getGroundY();
            double targetY = groundY + 1.0;
            double currentY = this.arthroee.getY();

            if (currentY < targetY) {
                this.arthroee.setDeltaMovement(this.arthroee.getDeltaMovement().add(0, 0.05, 0));
            } else if (currentY > targetY + 0.5) {
                this.arthroee.setDeltaMovement(this.arthroee.getDeltaMovement().add(0, -0.05, 0));
            } else {
                // Bobbing/hovering stability
                Vec3 delta = this.arthroee.getDeltaMovement();
                this.arthroee.setDeltaMovement(delta.x, delta.y * 0.8, delta.z);
            }
        }

        private double getGroundY() {
            BlockPos pos = this.arthroee.blockPosition();
            for (int i = 0; i < 16; i++) {
                BlockPos checkPos = pos.below(i);
                if (!this.arthroee.level().isEmptyBlock(checkPos)) {
                    return checkPos.getY() + 1.0;
                }
            }
            return this.arthroee.getY() - 5.0; // Fallback
        }

        private boolean canReach(Vec3 pos, int length) {
            AABB aabb = this.arthroee.getBoundingBox();
            for (int i = 1; i < length; ++i) {
                aabb = aabb.move(pos);
                if (!this.arthroee.level().noCollision(this.arthroee, aabb)) {
                    return false;
                }
            }
            return true;
        }
    }

    static class ArthroeeRandomFlyGoal extends Goal {
        private final ArthroeeEntity arthroee;

        public ArthroeeRandomFlyGoal(ArthroeeEntity arthroee) {
            this.arthroee = arthroee;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            MoveControl movecontrol = this.arthroee.getMoveControl();
            if (!movecontrol.hasWanted()) {
                return true;
            } else {
                double d0 = movecontrol.getWantedX() - this.arthroee.getX();
                double d1 = movecontrol.getWantedY() - this.arthroee.getY();
                double d2 = movecontrol.getWantedZ() - this.arthroee.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                return d3 < 1.0D || d3 > 3600.0D;
            }
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void start() {
            RandomSource randomsource = this.arthroee.getRandom();
            double d0 = this.arthroee.getX() + (double) ((randomsource.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d2 = this.arthroee.getZ() + (double) ((randomsource.nextFloat() * 2.0F - 1.0F) * 16.0F);

            // Find ground at target location
            BlockPos targetPos = BlockPos.containing(d0, this.arthroee.getY(), d2);
            double d1 = this.arthroee.getY();
            for (int i = -16; i < 16; i++) {
                BlockPos checkPos = targetPos.above(i);
                if (!this.arthroee.level().isEmptyBlock(checkPos) && this.arthroee.level().isEmptyBlock(checkPos.above())) {
                    d1 = checkPos.getY() + 2.0; // 1 block above ground
                    break;
                }
            }

            this.arthroee.getMoveControl().setWantedPosition(d0, d1, d2, 1.0D);
        }
    }

    private PlayState predicate(AnimationState<ArthroeeEntity> event) {
        if (event.getAnimatable().getAttackAnim(event.getPartialTick()) > 0) {
            return PlayState.CONTINUE;
        }
        event.setAnimation(RawAnimation.begin().thenLoop("idle"));
        return PlayState.CONTINUE;
    }

    private PlayState attackPredicate(AnimationState<ArthroeeEntity> event) {
        if (this.swinging) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("attack"));
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
        this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
    }

    @Override
    protected void playHurtSound(DamageSource pSource) {
        this.playSound(SoundEvents.SPIDER_HURT, 1.0F, 1.0F);
    }

    @Override
    public void playAmbientSound() {
        this.playSound(SoundEvents.SPIDER_AMBIENT, 0.15F, 1.0F);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SPIDER_DEATH;
    }
}

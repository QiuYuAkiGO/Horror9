////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package net.minecraft.world.entity.monster;
//
//import java.util.EnumSet;
//import net.minecraft.core.BlockPos;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.network.syncher.EntityDataAccessor;
//import net.minecraft.network.syncher.EntityDataSerializers;
//import net.minecraft.network.syncher.SynchedEntityData;
//import net.minecraft.sounds.SoundEvent;
//import net.minecraft.sounds.SoundEvents;
//import net.minecraft.sounds.SoundSource;
//import net.minecraft.tags.DamageTypeTags;
//import net.minecraft.util.Mth;
//import net.minecraft.util.RandomSource;
//import net.minecraft.world.Difficulty;
//import net.minecraft.world.damagesource.DamageSource;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.FlyingMob;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.Mob;
//import net.minecraft.world.entity.MobSpawnType;
//import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
//import net.minecraft.world.entity.ai.attributes.Attributes;
//import net.minecraft.world.entity.ai.control.MoveControl;
//import net.minecraft.world.entity.ai.goal.Goal;
//import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.entity.projectile.LargeFireball;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraft.world.phys.AABB;
//import net.minecraft.world.phys.Vec3;
//
//public class Ghast extends FlyingMob implements Enemy {
//    private static final EntityDataAccessor<Boolean> DATA_IS_CHARGING;
//    private int explosionPower = 1;
//
//    public Ghast(EntityType<? extends Ghast> entityType, Level level) {
//        super(entityType, level);
//        this.xpReward = 5;
//        this.moveControl = new GhastMoveControl(this);
//    }
//
//    protected void registerGoals() {
//        this.goalSelector.addGoal(5, new RandomFloatAroundGoal(this));
//        this.goalSelector.addGoal(7, new GhastLookGoal(this));
//        this.goalSelector.addGoal(7, new GhastShootFireballGoal(this));
//        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, 10, true, false, (p_352811_) -> Math.abs(p_352811_.getY() - this.getY()) <= (double)4.0F));
//    }
//
//    public boolean isCharging() {
//        return (Boolean)this.entityData.get(DATA_IS_CHARGING);
//    }
//
//    public void setCharging(boolean charging) {
//        this.entityData.set(DATA_IS_CHARGING, charging);
//    }
//
//    public int getExplosionPower() {
//        return this.explosionPower;
//    }
//
//    protected boolean shouldDespawnInPeaceful() {
//        return true;
//    }
//
//    private static boolean isReflectedFireball(DamageSource damageSource) {
//        return damageSource.getDirectEntity() instanceof LargeFireball && damageSource.getEntity() instanceof Player;
//    }
//
//    public boolean isInvulnerableTo(DamageSource source) {
//        return this.isInvulnerable() && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) || !isReflectedFireball(source) && super.isInvulnerableTo(source);
//    }
//
//    public boolean hurt(DamageSource source, float amount) {
//        if (isReflectedFireball(source)) {
//            super.hurt(source, 1000.0F);
//            return true;
//        } else {
//            return this.isInvulnerableTo(source) ? false : super.hurt(source, amount);
//        }
//    }
//
//    protected void defineSynchedData(SynchedEntityData.Builder builder) {
//        super.defineSynchedData(builder);
//        builder.define(DATA_IS_CHARGING, false);
//    }
//
//    public static AttributeSupplier.Builder createAttributes() {
//        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, (double)10.0F).add(Attributes.FOLLOW_RANGE, (double)100.0F);
//    }
//
//    public SoundSource getSoundSource() {
//        return SoundSource.HOSTILE;
//    }
//
//    protected SoundEvent getAmbientSound() {
//        return SoundEvents.GHAST_AMBIENT;
//    }
//
//    protected SoundEvent getHurtSound(DamageSource damageSource) {
//        return SoundEvents.GHAST_HURT;
//    }
//
//    protected SoundEvent getDeathSound() {
//        return SoundEvents.GHAST_DEATH;
//    }
//
//    protected float getSoundVolume() {
//        return 5.0F;
//    }
//
//    public static boolean checkGhastSpawnRules(EntityType<Ghast> ghast, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
//        return level.getDifficulty() != Difficulty.PEACEFUL && random.nextInt(20) == 0 && checkMobSpawnRules(ghast, level, spawnType, pos, random);
//    }
//
//    public int getMaxSpawnClusterSize() {
//        return 1;
//    }
//
//    public void addAdditionalSaveData(CompoundTag compound) {
//        super.addAdditionalSaveData(compound);
//        compound.putByte("ExplosionPower", (byte)this.explosionPower);
//    }
//
//    public void readAdditionalSaveData(CompoundTag compound) {
//        super.readAdditionalSaveData(compound);
//        if (compound.contains("ExplosionPower", 99)) {
//            this.explosionPower = compound.getByte("ExplosionPower");
//        }
//
//    }
//
//    static {
//        DATA_IS_CHARGING = SynchedEntityData.defineId(Ghast.class, EntityDataSerializers.BOOLEAN);
//    }
//
//    static class GhastLookGoal extends Goal {
//        private final Ghast ghast;
//
//        public GhastLookGoal(Ghast ghast) {
//            this.ghast = ghast;
//            this.setFlags(EnumSet.of(Flag.LOOK));
//        }
//
//        public boolean canUse() {
//            return true;
//        }
//
//        public boolean requiresUpdateEveryTick() {
//            return true;
//        }
//
//        public void tick() {
//            if (this.ghast.getTarget() == null) {
//                Vec3 vec3 = this.ghast.getDeltaMovement();
//                this.ghast.setYRot(-((float)Mth.atan2(vec3.x, vec3.z)) * (180F / (float)Math.PI));
//                this.ghast.yBodyRot = this.ghast.getYRot();
//            } else {
//                LivingEntity livingentity = this.ghast.getTarget();
//                double d0 = (double)64.0F;
//                if (livingentity.distanceToSqr(this.ghast) < (double)4096.0F) {
//                    double d1 = livingentity.getX() - this.ghast.getX();
//                    double d2 = livingentity.getZ() - this.ghast.getZ();
//                    this.ghast.setYRot(-((float)Mth.atan2(d1, d2)) * (180F / (float)Math.PI));
//                    this.ghast.yBodyRot = this.ghast.getYRot();
//                }
//            }
//
//        }
//    }
//
//    static class GhastMoveControl extends MoveControl {
//        private final Ghast ghast;
//        private int floatDuration;
//
//        public GhastMoveControl(Ghast ghast) {
//            super(ghast);
//            this.ghast = ghast;
//        }
//
//        public void tick() {
//            if (this.operation == Operation.MOVE_TO && this.floatDuration-- <= 0) {
//                this.floatDuration = this.floatDuration + this.ghast.getRandom().nextInt(5) + 2;
//                Vec3 vec3 = new Vec3(this.wantedX - this.ghast.getX(), this.wantedY - this.ghast.getY(), this.wantedZ - this.ghast.getZ());
//                double d0 = vec3.length();
//                vec3 = vec3.normalize();
//                if (this.canReach(vec3, Mth.ceil(d0))) {
//                    this.ghast.setDeltaMovement(this.ghast.getDeltaMovement().add(vec3.scale(0.1)));
//                } else {
//                    this.operation = Operation.WAIT;
//                }
//            }
//
//        }
//
//        private boolean canReach(Vec3 pos, int length) {
//            AABB aabb = this.ghast.getBoundingBox();
//
//            for(int i = 1; i < length; ++i) {
//                aabb = aabb.move(pos);
//                if (!this.ghast.level().noCollision(this.ghast, aabb)) {
//                    return false;
//                }
//            }
//
//            return true;
//        }
//    }
//
//    static class GhastShootFireballGoal extends Goal {
//        private final Ghast ghast;
//        public int chargeTime;
//
//        public GhastShootFireballGoal(Ghast ghast) {
//            this.ghast = ghast;
//        }
//
//        public boolean canUse() {
//            return this.ghast.getTarget() != null;
//        }
//
//        public void start() {
//            this.chargeTime = 0;
//        }
//
//        public void stop() {
//            this.ghast.setCharging(false);
//        }
//
//        public boolean requiresUpdateEveryTick() {
//            return true;
//        }
//
//        public void tick() {
//            LivingEntity livingentity = this.ghast.getTarget();
//            if (livingentity != null) {
//                double d0 = (double)64.0F;
//                if (livingentity.distanceToSqr(this.ghast) < (double)4096.0F && this.ghast.hasLineOfSight(livingentity)) {
//                    Level level = this.ghast.level();
//                    ++this.chargeTime;
//                    if (this.chargeTime == 10 && !this.ghast.isSilent()) {
//                        level.levelEvent((Player)null, 1015, this.ghast.blockPosition(), 0);
//                    }
//
//                    if (this.chargeTime == 20) {
//                        double d1 = (double)4.0F;
//                        Vec3 vec3 = this.ghast.getViewVector(1.0F);
//                        double d2 = livingentity.getX() - (this.ghast.getX() + vec3.x * (double)4.0F);
//                        double d3 = livingentity.getY((double)0.5F) - ((double)0.5F + this.ghast.getY((double)0.5F));
//                        double d4 = livingentity.getZ() - (this.ghast.getZ() + vec3.z * (double)4.0F);
//                        Vec3 vec31 = new Vec3(d2, d3, d4);
//                        if (!this.ghast.isSilent()) {
//                            level.levelEvent((Player)null, 1016, this.ghast.blockPosition(), 0);
//                        }
//
//                        LargeFireball largefireball = new LargeFireball(level, this.ghast, vec31.normalize(), this.ghast.getExplosionPower());
//                        largefireball.setPos(this.ghast.getX() + vec3.x * (double)4.0F, this.ghast.getY((double)0.5F) + (double)0.5F, largefireball.getZ() + vec3.z * (double)4.0F);
//                        level.addFreshEntity(largefireball);
//                        this.chargeTime = -40;
//                    }
//                } else if (this.chargeTime > 0) {
//                    --this.chargeTime;
//                }
//
//                this.ghast.setCharging(this.chargeTime > 10);
//            }
//
//        }
//    }
//
//    static class RandomFloatAroundGoal extends Goal {
//        private final Ghast ghast;
//
//        public RandomFloatAroundGoal(Ghast ghast) {
//            this.ghast = ghast;
//            this.setFlags(EnumSet.of(Flag.MOVE));
//        }
//
//        public boolean canUse() {
//            MoveControl movecontrol = this.ghast.getMoveControl();
//            if (!movecontrol.hasWanted()) {
//                return true;
//            } else {
//                double d0 = movecontrol.getWantedX() - this.ghast.getX();
//                double d1 = movecontrol.getWantedY() - this.ghast.getY();
//                double d2 = movecontrol.getWantedZ() - this.ghast.getZ();
//                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
//                return d3 < (double)1.0F || d3 > (double)3600.0F;
//            }
//        }
//
//        public boolean canContinueToUse() {
//            return false;
//        }
//
//        public void start() {
//            RandomSource randomsource = this.ghast.getRandom();
//            double d0 = this.ghast.getX() + (double)((randomsource.nextFloat() * 2.0F - 1.0F) * 16.0F);
//            double d1 = this.ghast.getY() + (double)((randomsource.nextFloat() * 2.0F - 1.0F) * 16.0F);
//            double d2 = this.ghast.getZ() + (double)((randomsource.nextFloat() * 2.0F - 1.0F) * 16.0F);
//            this.ghast.getMoveControl().setWantedPosition(d0, d1, d2, (double)1.0F);
//        }
//    }
//}

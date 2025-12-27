package net.qiuyu.horror9.entity.custom;

import net.qiuyu.horror9.entity.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
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
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BiterEntity extends Monster implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public static final float STARTING_ANGLE = 0.0174532925F;
    public BiterEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    // 设置实体基础属性
    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D) // 最大生命值
                .add(Attributes.ATTACK_DAMAGE, 4.0f) // 攻击力
                .add(Attributes.ATTACK_SPEED, 1.0f) // 攻击速度
                .add(Attributes.MOVEMENT_SPEED, 0.3f) // 移动速度
                .add(Attributes.ATTACK_KNOCKBACK, 0.5f) // 击退力
                .build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }
    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
            pPlayer.addEffect(new MobEffectInstance(MobEffects.POISON,
                    pPlayer.hasEffect(MobEffects.POISON) ? pPlayer.getEffect(MobEffects.POISON).getDuration() + 100: 100, 0));
            pPlayer.addEffect(new MobEffectInstance(MobEffects.CONFUSION,
                    pPlayer.hasEffect(MobEffects.CONFUSION) ? pPlayer.getEffect(MobEffects.CONFUSION).getDuration()  + 100: 100, 0));
            pPlayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,
                    pPlayer.hasEffect(MobEffects.BLINDNESS) ? pPlayer.getEffect(MobEffects.BLINDNESS).getDuration()  + 100: 100, 0));
            pPlayer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
                    pPlayer.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) ? pPlayer.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getDuration()  + 100: 100, 1));
        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    public void rideTick() {
        final Entity entity = this.getVehicle();
        if (this.isPassenger() && entity != null && !entity.isAlive()) {
            this.stopRiding();
        } else {
            this.setDeltaMovement(0, 0, 0);
            this.tick();
            if (this.isPassenger()) {
                final Entity mount = this.getVehicle();
                if (mount instanceof final LivingEntity livingEntity) {
                    this.yBodyRot = livingEntity.yBodyRot;
                    this.setYRot(livingEntity.getYRot());
                    this.yHeadRot = livingEntity.yHeadRot;
                    this.yRotO = livingEntity.yHeadRot;
                    final float radius = 1F;
                    final float angle = (STARTING_ANGLE * livingEntity.yBodyRot);
                    final double extraX = radius * Mth.sin(Mth.PI + angle);
                    final double extraZ = radius * Mth.cos(angle);
                    this.setPos(mount.getX() + extraX, Math.max(mount.getY() + mount.getEyeHeight() * 0.25F, mount.getY()), mount.getZ() + extraZ);
                    if (!mount.isAlive() || mount instanceof Player && ((Player) mount).isCreative()) {
                        this.removeVehicle();
                    }
                    if (!this.level().isClientSide && this.isAlive() ) {
                        mount.hurt(this.damageSources().mobAttack(this),6.0F);
                        if (!mount.isAlive()){
                            this.level().addParticle(new SimpleParticleType(false), mount.getX(), mount.getY(), mount.getZ(), 10, 0.5, 0.5);
                            Husk husk = EntityType.HUSK.create(this.level());
                            if (husk != null) {
                                husk.moveTo(mount.getX(), mount.getY(), mount.getZ(), mount.getYRot(), mount.getXRot());
                                if (mount instanceof Player player) {
                                    for (EquipmentSlot slot : EquipmentSlot.values()) {
                                        ItemStack stack = player.getItemBySlot(slot);
                                        if (!stack.isEmpty()) {
                                            husk.setItemSlot(slot, stack.copy());
                                            husk.setDropChance(slot, 0.0f);
                                        }
                                    }
                                }
                                this.level().addFreshEntity(husk);
                            }

                            for (int i = 0; i < 2; i++) {
                                BiterEntity biter = ModEntityTypes.BITER.get().create(this.level());
                                if (biter != null) {
                                    biter.moveTo(mount.getX(), mount.getY(), mount.getZ(), mount.getYRot(), mount.getXRot());
                                    this.level().addFreshEntity(biter);
                                }
                            }
                        }
                        if(mount instanceof LivingEntity){
                            ((LivingEntity) mount).addEffect(new MobEffectInstance(MobEffects.POISON,
                                    ((LivingEntity) mount).hasEffect(MobEffects.POISON) ? ((LivingEntity) mount).getEffect(MobEffects.POISON).getDuration() + 100: 100, 0));
                            ((LivingEntity) mount).addEffect(new MobEffectInstance(MobEffects.CONFUSION,
                                    ((LivingEntity) mount).hasEffect(MobEffects.CONFUSION) ? ((LivingEntity) mount).getEffect(MobEffects.CONFUSION).getDuration()  + 100: 100, 0));
                            ((LivingEntity) mount).addEffect(new MobEffectInstance(MobEffects.BLINDNESS,
                                    ((LivingEntity) mount).hasEffect(MobEffects.BLINDNESS) ? ((LivingEntity) mount).getEffect(MobEffects.BLINDNESS).getDuration()  + 100: 100, 0));
                            ((LivingEntity) mount).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
                                    ((LivingEntity) mount).hasEffect(MobEffects.MOVEMENT_SLOWDOWN) ? ((LivingEntity) mount).getEffect(MobEffects.MOVEMENT_SLOWDOWN).getDuration()  + 100: 100, 1));
                        }
                    }
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
            event.resetCurrentAnimation();
            // 攻击动画名
            event.setAnimation(RawAnimation.begin().then("attack", Animation.LoopType.PLAY_ONCE));
            this.swinging = false;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
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
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SPIDER_AMBIENT;
    }

    @Override
    protected @NotNull SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return SoundEvents.SPIDER_HURT;
    }

    @Override
    protected @NotNull SoundEvent getDeathSound() {
        return SoundEvents.SPIDER_DEATH;
    }

}

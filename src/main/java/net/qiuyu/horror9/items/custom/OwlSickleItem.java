package net.qiuyu.horror9.items.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class OwlSickleItem extends SwordItem implements Vanishable {
//    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public OwlSickleItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {

        if (pAttacker instanceof Player) {
            pAttacker.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 100, 1));
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

//    @Override
//    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
//
//    }
//
//    @Override
//    public AnimatableInstanceCache getAnimatableInstanceCache() {
//        return cache;
//    }
}

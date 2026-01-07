package net.qiuyu.horror9.entity.custom;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.message.CrashPlayerMsg;
import net.qiuyu.horror9.entity.ModEntityTypes;
import net.qiuyu.horror9.register.ModItems;

public class NullTridentEntity extends ThrownTrident {
    public NullTridentEntity(EntityType<? extends NullTridentEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public NullTridentEntity(Level pLevel, LivingEntity pShooter, ItemStack pStack) {
        super(pLevel, pShooter, pStack);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        Entity target = pResult.getEntity();
        if (!this.level().isClientSide) {
            if (target instanceof ServerPlayer serverPlayer) {
                Horror9.sendNonLocal(new CrashPlayerMsg(), serverPlayer);
            } else if (!(target instanceof Player)) {
                target.discard();
            }
        }
        super.onHitEntity(pResult);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.NULL_TRIDENT.get());
    }
}

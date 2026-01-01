package net.qiuyu.horror9.event;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.entity.ModEntityTypes;
import net.qiuyu.horror9.entity.custom.BiterEntity;
import net.qiuyu.horror9.entity.custom.No1Entity;
import net.qiuyu.horror9.entity.custom.TheMistakenEntity;
import net.qiuyu.horror9.register.ModItems;

public class ModEvents {

    @Mod.EventBusSubscriber(modid = Horror9.MODID)
    public static class ForgeEvents {

        @SubscribeEvent
        public static void onLivingHurt(LivingHurtEvent event) {
            if (event.getSource().getEntity() instanceof Player player) {
                if (player.getMainHandItem().is(ModItems.OWL_SICKLE.get()) && player.hasEffect(MobEffects.DARKNESS) && player.getAttackStrengthScale(0.0F) >= 1.0F) {
                    event.setAmount(event.getAmount() + 6.0f);
                }
            }
        }

        @SubscribeEvent
        public static void onLivingDeath(LivingDeathEvent event) {
            if (event.getEntity() instanceof Player player && !player.level().isClientSide()) {
                if (event.getSource().getEntity() instanceof LivingEntity attacker && attacker.getMainHandItem().is(ModItems.OWL_SICKLE.get())) {
                    ItemStack head = new ItemStack(Items.PLAYER_HEAD);
                    head.getOrCreateTag().putString("SkullOwner", player.getName().getString());

                    ItemEntity itemEntity = new ItemEntity(player.level(), player.getX(), player.getY(), player.getZ(), head);
//                    itemEntity.setPickUpDelay(10);
                    player.level().addFreshEntity(itemEntity);
                }
            }
        }


        @Mod.EventBusSubscriber(modid = Horror9.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
        public static class ModEventBusEvents {
            @SubscribeEvent
            public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
                event.put(ModEntityTypes.NO1.get(), No1Entity.setAttributes());
                event.put(ModEntityTypes.BITER.get(), BiterEntity.setAttributes());
                event.put(ModEntityTypes.THE_MISTAKEN.get(), TheMistakenEntity.setAttributes());
            }
        }
    }
}
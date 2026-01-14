package net.qiuyu.horror9.event;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
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
import top.theillusivec4.curios.api.CuriosApi;

public class ModEvents {

    @Mod.EventBusSubscriber(modid = Horror9.MODID)
    public static class ForgeEvents {

        @SubscribeEvent
        @SuppressWarnings("removal")
        public static void onLivingHurt(LivingHurtEvent event) {
            LivingEntity victim = event.getEntity();
            Entity attacker = event.getSource().getEntity();

            if (attacker instanceof Player player) {
                if (player.getMainHandItem().is(ModItems.OWL_SICKLE.get()) && player.hasEffect(MobEffects.DARKNESS) && player.getAttackStrengthScale(0.0F) >= 1.0F) {
                    event.setAmount(event.getAmount() + 6.0f);
                }
                if (player.getMainHandItem().is(ModItems.HEART_PASS.get()) && player.getAttackStrengthScale(0.0F) >= 1.0F) {
                    victim.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 1200, 1));
                }
            }

            if (!victim.level().isClientSide()) {
                //noinspection UnstableApiUsage
                CuriosApi.getCuriosHelper().findFirstCurio(victim, stack -> stack.is(ModItems.HEART_METAL.get())).ifPresent(slotResult -> {
                    if (victim.getRandom().nextFloat() < 0.3f) {
                        victim.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 400, 0));
                    }
                });
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
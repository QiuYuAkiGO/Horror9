package net.qiuyu.horror9.event;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
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
                CuriosApi.getCuriosInventory(victim).map(handler -> handler.findFirstCurio(stack -> stack.is(ModItems.HEART_METAL.get())))
                        .ifPresent(slotResult -> {
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

                // Creator Phone protection
                for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                    ItemStack stack = player.getInventory().getItem(i);
                    if (stack.is(ModItems.CREATOR_PHONE.get())) {
                        IEnergyStorage energy = stack.getCapability(ForgeCapabilities.ENERGY).orElse(null);
                        if (energy != null && energy.getEnergyStored() > 0) {
                            event.setCanceled(true);
                            energy.extractEnergy(energy.getEnergyStored(), false);
                            player.setHealth(1.0f);
                            player.removeAllEffects();
                            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
                            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
                            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));

                            player.level().playSound(player, player.getX(), player.getY(), player.getZ(),
                                    SoundEvents.TOTEM_USE, player.getSoundSource(), 1.0F, 1.0F);
                            if (player.level() instanceof ServerLevel serverLevel) {
                                serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING,
                                        player.getX(), player.getY() + 1.0, player.getZ(),
                                        60, 0.5, 0.5, 0.5, 0.2);
                            }
                            break;
                        }
                    }
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
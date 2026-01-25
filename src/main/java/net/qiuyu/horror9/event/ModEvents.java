package net.qiuyu.horror9.event;

import net.minecraft.core.component.DataComponents;
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
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.entity.ModEntityTypes;
import net.qiuyu.horror9.entity.custom.BiterEntity;
import net.qiuyu.horror9.entity.custom.No1Entity;
import net.qiuyu.horror9.entity.custom.TheMistakenEntity;
import net.qiuyu.horror9.register.ModItems;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

public class ModEvents {

    @EventBusSubscriber(modid = Horror9.MODID)
    public static class ForgeEvents {

        @SubscribeEvent
        public static void registerCapabilities(net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent event) {
            event.registerItem(Capabilities.EnergyStorage.ITEM, (stack, ctx) -> new net.qiuyu.horror9.items.custom.CreatorPhoneItem.ItemEnergyStorage(stack, net.qiuyu.horror9.items.custom.CreatorPhoneItem.MAX_ENERGY), ModItems.CREATOR_PHONE.get());
        }

        @SubscribeEvent
        public static void onLivingFall(LivingFallEvent event) {
            LivingEntity entity = event.getEntity();
            if (entity instanceof Player player && !player.level().isClientSide()) {
                float fallDistance = event.getDistance();
                if (fallDistance > 3.0f) {
                    CuriosApi.getCuriosInventory(player).map(handler -> handler.findFirstCurio(stack -> stack.is(ModItems.YUUKA_HALO.get())))
                            .ifPresent(slotResult -> {
                                Level level = player.level();
                                // 产生视觉爆炸，半径随距离略微增加
                                level.explode(player, player.getX(), player.getY(), player.getZ(), 2.0f + fallDistance / 10.0f, Level.ExplosionInteraction.NONE);

                                // 手动计算半径内的伤害，实现 1:1 的比例
                                float radius = 3.0f + fallDistance / 5.0f;
                                List<Entity> entities = level.getEntities(player, player.getBoundingBox().inflate(radius));
                                for (Entity target : entities) {
                                    if (target instanceof LivingEntity livingTarget) {
                                        float distance = player.distanceTo(target);
                                        if (distance <= radius) {
                                            // 伤害和摔落距离呈 1:1 比例（在爆炸中心时）
                                            float damage = fallDistance * (1.0f - distance / radius);
                                            livingTarget.hurt(level.damageSources().explosion(player, player), damage);
                                        }
                                    }
                                }
                            });
                }
            }
        }

        @SubscribeEvent
        public static void onLivingHurt(LivingIncomingDamageEvent event) {
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
                    head.set(DataComponents.PROFILE, new ResolvableProfile(player.getGameProfile()));

                    ItemEntity itemEntity = new ItemEntity(player.level(), player.getX(), player.getY(), player.getZ(), head);
//                    itemEntity.setPickUpDelay(10);
                    player.level().addFreshEntity(itemEntity);
                }

                // Creator Phone protection
                for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                    ItemStack stack = player.getInventory().getItem(i);
                    if (stack.is(ModItems.CREATOR_PHONE.get())) {
                        IEnergyStorage energy = stack.getCapability(Capabilities.EnergyStorage.ITEM);
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


        @EventBusSubscriber(modid = Horror9.MODID, bus = EventBusSubscriber.Bus.MOD)
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
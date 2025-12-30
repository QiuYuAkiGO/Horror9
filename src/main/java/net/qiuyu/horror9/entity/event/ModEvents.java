package net.qiuyu.horror9.entity.event;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
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
            if (event.getSource().getEntity() instanceof LivingEntity attacker) {
                // 检查攻击者是否手持 Owl Sickle 且拥有黑暗效果
                if (attacker.getMainHandItem().is(ModItems.OWL_SICKLE.get()) && attacker.hasEffect(MobEffects.DARKNESS)) {
                    event.setAmount(event.getAmount() + 6.0f);
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
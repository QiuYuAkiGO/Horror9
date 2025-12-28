package net.qiuyu.horror9.entity.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.entity.ModEntityTypes;
import net.qiuyu.horror9.entity.custom.BiterEntity;
import net.qiuyu.horror9.entity.custom.No1Entity;
import net.qiuyu.horror9.entity.custom.TheMistakenEntity;

public class ModEvents {

    @Mod.EventBusSubscriber(modid = Horror9.MODID)
    public static class ForgeEvents {
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
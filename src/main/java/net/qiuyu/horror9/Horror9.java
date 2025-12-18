package net.qiuyu.horror9;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.qiuyu.horror9.entity.ModEntityTypes;
import net.qiuyu.horror9.entity.item.ModCreativeModeTab;
import net.qiuyu.horror9.entity.item.ModItems;
import net.qiuyu.horror9.entity.renderer.No1Renderer;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

@Mod(Horror9.MODID) public class Horror9 {

    public static final String MODID = "horror9";
//    private static final Logger LOGGER = LogUtils.getLogger();


    public Horror9(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        ModItems.register(modEventBus);
        ModEntityTypes.register(modEventBus);
        ModCreativeModeTab.register(modEventBus);

        GeckoLib.initialize();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntityTypes.NO1.get(), No1Renderer::new);
        }
    }
    private void commonSetup(final FMLCommonSetupEvent event)
    {}
}

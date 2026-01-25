package net.qiuyu.horror9;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.fml.event.lifecycle.InterModProcessEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.qiuyu.horror9.armor.ModArmorMaterials;
import net.qiuyu.horror9.compat.Curios;
import net.qiuyu.horror9.entity.ModEntityTypes;
import net.qiuyu.horror9.entity.renderer.*;
import net.qiuyu.horror9.register.ModCreativeModeTab;
import net.qiuyu.horror9.register.ModItems;
import net.qiuyu.horror9.items.renderer.HeartMetalRenderer;
import net.qiuyu.horror9.items.renderer.YuukaHaloRenderer;
import org.slf4j.Logger;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import java.util.stream.Collectors;

@Mod(Horror9.MODID)
public class Horror9 {

    public static final String MODID = "horror9";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final CommonProxy PROXY = FMLEnvironment.dist == Dist.CLIENT ? new ClientProxy() : new CommonProxy();

    public Horror9(IEventBus modEventBus, ModContainer modContainer) {
        ModItems.register(modEventBus);
        ModArmorMaterials.register(modEventBus);
        ModEntityTypes.register(modEventBus);
        ModCreativeModeTab.register(modEventBus);

        // GeckoLib.initialize();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::enqueueIMC);
        modEventBus.addListener(this::processIMC);
    }


    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    public static <MSG extends CustomPacketPayload> void sendMSGToServer(MSG message) {
        PacketDistributor.sendToServer(message);
    }

    public static <MSG extends CustomPacketPayload> void sendMSGToAll(MSG message) {
        PacketDistributor.sendToAllPlayers(message);
    }

    public static <MSG extends CustomPacketPayload> void sendNonLocal(MSG msg, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, msg);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        Curios.registerCurioSlot(Curios.CHEST_SLOT, 1, false, null);
        Curios.registerCurioSlot(Curios.HALO_SLOT, 1, false, null);
    }

    private void processIMC(final InterModProcessEvent event) {
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.messageSupplier().get()).
                collect(Collectors.toList()));
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntityTypes.NO1.get(), No1Renderer::new);
            EntityRenderers.register(ModEntityTypes.BITER.get(), BiterRenderer::new);
            EntityRenderers.register(ModEntityTypes.THE_MISTAKEN.get(), TheMistakenRenderer::new);
            EntityRenderers.register(ModEntityTypes.NULL_TRIDENT_ENTITY.get(), NullTridentEntityRenderer::new);
            CuriosRendererRegistry.register(ModItems.HEART_METAL.get(), HeartMetalRenderer::new);
            CuriosRendererRegistry.register(ModItems.YUUKA_HALO.get(), YuukaHaloRenderer::new);
            EntityRenderers.register(ModEntityTypes.WITHER_BOMB.get(), WitherBombRenderer::new);
        }

        @SubscribeEvent
        public static void onModelRegister(ModelEvent.RegisterAdditional event) {
            event.register(new ModelResourceLocation(ResourceLocation.parse(Horror9.MODID + ":item/null_trident_2d"), "standalone"));
        }
    }
}

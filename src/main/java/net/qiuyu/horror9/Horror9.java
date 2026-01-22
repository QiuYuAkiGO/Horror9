package net.qiuyu.horror9;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.qiuyu.horror9.compat.Curios;
import net.qiuyu.horror9.entity.ModEntityTypes;
import net.qiuyu.horror9.entity.renderer.*;
import net.qiuyu.horror9.register.ModCreativeModeTab;
import net.qiuyu.horror9.register.ModItems;
import net.qiuyu.horror9.message.BiterDismountMsg;
import net.qiuyu.horror9.message.BiterMountPlayerMsg;
import net.qiuyu.horror9.message.CrashPlayerMsg;
import net.qiuyu.horror9.message.CreatorPhoneTeleportMsg;
import net.qiuyu.horror9.message.HuntingHornNoteMsg;
import net.qiuyu.horror9.items.renderer.HeartMetalRenderer;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import java.util.stream.Collectors;

@Mod(Horror9.MODID) public class Horror9 {

    public static final String MODID = "horror9";
    public static final Logger LOGGER = LogUtils.getLogger();
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    private static int packetsRegistered;
    public static final CommonProxy PROXY = DistExecutor.unsafeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
    public static final SimpleChannel NETWORK_WRAPPER ;

    static {
        NetworkRegistry.ChannelBuilder channel = NetworkRegistry.ChannelBuilder.named(ResourceLocation.parse(Horror9.MODID + ":" + "main_channel"));
        String version = PROTOCOL_VERSION;
        channel = channel.clientAcceptedVersions(version::equals);
        version = PROTOCOL_VERSION;
        NETWORK_WRAPPER = channel.serverAcceptedVersions(version::equals).networkProtocolVersion(() -> PROTOCOL_VERSION).simpleChannel();
    }

    public Horror9(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        ModItems.register(modEventBus);
        ModEntityTypes.register(modEventBus);
        ModCreativeModeTab.register(modEventBus);

        GeckoLib.initialize();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::enqueueIMC);
        modEventBus.addListener(this::processIMC);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        NETWORK_WRAPPER.registerMessage(packetsRegistered++, BiterMountPlayerMsg.class, BiterMountPlayerMsg::write, BiterMountPlayerMsg::read, BiterMountPlayerMsg.Handler::handle);
        NETWORK_WRAPPER.registerMessage(packetsRegistered++, BiterDismountMsg.class, BiterDismountMsg::write, BiterDismountMsg::read, BiterDismountMsg.Handler::handle);
        NETWORK_WRAPPER.registerMessage(packetsRegistered++, HuntingHornNoteMsg.class, HuntingHornNoteMsg::write, HuntingHornNoteMsg::read, HuntingHornNoteMsg.Handler::handle);
        NETWORK_WRAPPER.registerMessage(packetsRegistered++, CrashPlayerMsg.class, CrashPlayerMsg::write, CrashPlayerMsg::read, CrashPlayerMsg.Handler::handle);
        NETWORK_WRAPPER.registerMessage(packetsRegistered++, CreatorPhoneTeleportMsg.class, CreatorPhoneTeleportMsg::write, CreatorPhoneTeleportMsg::read, CreatorPhoneTeleportMsg.Handler::handle);
    }

    public static <MSG> void sendMSGToServer(MSG message) {
        NETWORK_WRAPPER.sendToServer(message);
    }

    public static <MSG> void sendMSGToAll(MSG message) {
        for (ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
            sendNonLocal(message, player);
        }
    }

    public static <MSG> void sendNonLocal(MSG msg, ServerPlayer player) {
        NETWORK_WRAPPER.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        Curios.registerCurioSlot(Curios.CHEST_SLOT, 1, false, null);
    }

    private void processIMC(final InterModProcessEvent event) {
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.messageSupplier().get()).
                collect(Collectors.toList()));
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntityTypes.NO1.get(), No1Renderer::new);
            EntityRenderers.register(ModEntityTypes.BITER.get(), BiterRenderer::new);
            EntityRenderers.register(ModEntityTypes.THE_MISTAKEN.get(), TheMistakenRenderer::new);
            EntityRenderers.register(ModEntityTypes.NULL_TRIDENT_ENTITY.get(), NullTridentEntityRenderer::new);
            CuriosRendererRegistry.register(ModItems.HEART_METAL.get(), HeartMetalRenderer::new);
            EntityRenderers.register(ModEntityTypes.WITHER_BOMB.get(), WitherBombRenderer::new);
        }

        @SubscribeEvent
        public static void onModelRegister(ModelEvent.RegisterAdditional event) {
            event.register(ResourceLocation.parse(Horror9.MODID + ":item/null_trident_2d"));
        }
    }
}

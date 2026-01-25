package net.qiuyu.horror9.message;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.qiuyu.horror9.Horror9;

@EventBusSubscriber(modid = Horror9.MODID)
public class ModMessages {

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playBidirectional(
                BiterMountPlayerMsg.TYPE,
                BiterMountPlayerMsg.STREAM_CODEC,
                BiterMountPlayerMsg::handle
        );
        registrar.playBidirectional(
                BiterDismountMsg.TYPE,
                BiterDismountMsg.STREAM_CODEC,
                BiterDismountMsg::handle
        );
        registrar.playToServer(
                HuntingHornNoteMsg.TYPE,
                HuntingHornNoteMsg.STREAM_CODEC,
                HuntingHornNoteMsg::handle
        );
        registrar.playToClient(
                CrashPlayerMsg.TYPE,
                CrashPlayerMsg.STREAM_CODEC,
                CrashPlayerMsg::handle
        );
        registrar.playToServer(
                CreatorPhoneTeleportMsg.TYPE,
                CreatorPhoneTeleportMsg.STREAM_CODEC,
                CreatorPhoneTeleportMsg::handle
        );
    }
}

package net.qiuyu.horror9.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.entity.custom.BiterEntity;

public record BiterMountPlayerMsg(int rider, int mount) implements CustomPacketPayload {

    public static final Type<BiterMountPlayerMsg> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Horror9.MODID, "biter_mount_player"));

    public static final StreamCodec<ByteBuf, BiterMountPlayerMsg> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, BiterMountPlayerMsg::rider,
            ByteBufCodecs.INT, BiterMountPlayerMsg::mount,
            BiterMountPlayerMsg::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(BiterMountPlayerMsg message, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            // Client side handling handled by context.player() being the client player?
            // On server, it's the sender.
            // On client, it's the client player.
            
            if (player != null) {
                Entity entity = player.level().getEntity(message.rider);
                Entity mountEntity = player.level().getEntity(message.mount);
                if ((entity instanceof BiterEntity) && mountEntity instanceof Player && entity.distanceTo(mountEntity) < 16D) {
                    entity.startRiding(mountEntity, true);
                }
            }
        });
    }
}
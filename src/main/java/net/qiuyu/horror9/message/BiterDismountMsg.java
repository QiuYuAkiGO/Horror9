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

public record BiterDismountMsg(int rider, int mount) implements CustomPacketPayload {

    public static final Type<BiterDismountMsg> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Horror9.MODID, "biter_dismount_player"));

    public static final StreamCodec<ByteBuf, BiterDismountMsg> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, BiterDismountMsg::rider,
            ByteBufCodecs.INT, BiterDismountMsg::mount,
            BiterDismountMsg::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(BiterDismountMsg message, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player != null) {
                Entity entity = player.level().getEntity(message.rider);
                Entity mountEntity = player.level().getEntity(message.mount);
                if ((entity instanceof BiterEntity) && mountEntity != null) {
                    entity.stopRiding();
                }
            }
        });
    }
}
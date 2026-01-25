package net.qiuyu.horror9.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.qiuyu.horror9.Horror9;

public record CreatorPhoneTeleportMsg(String targetName) implements CustomPacketPayload {

    public static final Type<CreatorPhoneTeleportMsg> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Horror9.MODID, "creator_phone_teleport"));

    public static final StreamCodec<ByteBuf, CreatorPhoneTeleportMsg> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, CreatorPhoneTeleportMsg::targetName,
            CreatorPhoneTeleportMsg::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(CreatorPhoneTeleportMsg msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (ctx.player() instanceof ServerPlayer player) {
                ServerPlayer target = player.getServer().getPlayerList().getPlayerByName(msg.targetName);
                if (target != null) {
                    player.teleportTo(target.serverLevel(), target.getX(), target.getY(), target.getZ(), target.getYRot(), target.getXRot());
                }
            }
        });
    }
}

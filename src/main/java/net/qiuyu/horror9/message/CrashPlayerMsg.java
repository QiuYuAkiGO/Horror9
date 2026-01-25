package net.qiuyu.horror9.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.qiuyu.horror9.Horror9;

public record CrashPlayerMsg() implements CustomPacketPayload {

    public static final Type<CrashPlayerMsg> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Horror9.MODID, "crash_player"));

    public static final StreamCodec<ByteBuf, CrashPlayerMsg> STREAM_CODEC = StreamCodec.unit(new CrashPlayerMsg());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(CrashPlayerMsg message, IPayloadContext context) {
        // 抛出 NullPointerException 导致客户端崩溃
        throw new NullPointerException("Forcefully crashed by Null Trident");
    }
}

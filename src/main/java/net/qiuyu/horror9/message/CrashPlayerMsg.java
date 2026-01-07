package net.qiuyu.horror9.message;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CrashPlayerMsg {
    public CrashPlayerMsg() {
    }

    public static CrashPlayerMsg read(FriendlyByteBuf buf) {
        return new CrashPlayerMsg();
    }

    public static void write(CrashPlayerMsg message, FriendlyByteBuf buf) {
    }

    public static class Handler {
        public static void handle(CrashPlayerMsg message, Supplier<NetworkEvent.Context> context) {
            context.get().setPacketHandled(true);
            // 抛出 NullPointerException 导致客户端崩溃
            throw new NullPointerException("Forcefully crashed by Null Trident");
        }
    }
}

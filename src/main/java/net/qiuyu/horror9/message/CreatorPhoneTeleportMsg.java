package net.qiuyu.horror9.message;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CreatorPhoneTeleportMsg {
    private final String targetName;

    public CreatorPhoneTeleportMsg(String targetName) {
        this.targetName = targetName;
    }

    public static void write(CreatorPhoneTeleportMsg msg, FriendlyByteBuf buf) {
        buf.writeUtf(msg.targetName);
    }

    public static CreatorPhoneTeleportMsg read(FriendlyByteBuf buf) {
        return new CreatorPhoneTeleportMsg(buf.readUtf());
    }

    public static class Handler {
        public static void handle(CreatorPhoneTeleportMsg msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();
                if (player != null) {
                    ServerPlayer target = player.getServer().getPlayerList().getPlayerByName(msg.targetName);
                    if (target != null) {
                        player.teleportTo(target.serverLevel(), target.getX(), target.getY(), target.getZ(), target.getYRot(), target.getXRot());
                    }
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}

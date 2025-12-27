package net.qiuyu.horror9.message;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.entity.custom.BiterEntity;

import java.util.function.Supplier;

public class BiterDismountMsg {

    public int rider;
    public int mount;

    public BiterDismountMsg(int rider, int mount) {
        this.rider = rider;
        this.mount = mount;
    }

    public static BiterDismountMsg read(FriendlyByteBuf buf) {
        return new BiterDismountMsg(buf.readInt(), buf.readInt());
    }

    public static void write(BiterDismountMsg message, FriendlyByteBuf buf) {
        buf.writeInt(message.rider);
        buf.writeInt(message.mount);
    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(BiterDismountMsg message, Supplier<NetworkEvent.Context> context) {
            context.get().setPacketHandled(true);
            context.get().enqueueWork(() -> {
                Player player = context.get().getSender();
                if (context.get().getDirection().getReceptionSide() == LogicalSide.CLIENT) {
                    player = Horror9.PROXY.getClientSidePlayer();
                }

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
}
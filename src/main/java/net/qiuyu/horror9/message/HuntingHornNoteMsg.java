package net.qiuyu.horror9.message;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.qiuyu.horror9.items.custom.HuntingHornItem;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class HuntingHornNoteMsg {
    private final int noteType;

    public HuntingHornNoteMsg(int noteType) {
        this.noteType = noteType;
    }

    public static HuntingHornNoteMsg read(FriendlyByteBuf buf) {
        return new HuntingHornNoteMsg(buf.readInt());
    }

    public static void write(HuntingHornNoteMsg message, FriendlyByteBuf buf) {
        buf.writeInt(message.noteType);
    }

    public static class Handler {
        public static void handle(HuntingHornNoteMsg message, Supplier<NetworkEvent.Context> context) {
            context.get().setPacketHandled(true);
            context.get().enqueueWork(() -> {
                Player player = context.get().getSender();
                if (player != null) {
                    ItemStack stack = player.getMainHandItem();
                    if (stack.getItem() instanceof HuntingHornItem hornItem) {
                        hornItem.addNote(stack, player, message.noteType);
                    } else {
                        stack = player.getOffhandItem();
                        if (stack.getItem() instanceof HuntingHornItem hornItem) {
                            hornItem.addNote(stack, player, message.noteType);
                        }
                    }
                }
            });
        }
    }
}

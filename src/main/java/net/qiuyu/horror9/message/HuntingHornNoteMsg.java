package net.qiuyu.horror9.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.items.custom.HuntingHornItem;

public record HuntingHornNoteMsg(int noteType) implements CustomPacketPayload {

    public static final Type<HuntingHornNoteMsg> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Horror9.MODID, "hunting_horn_note"));

    public static final StreamCodec<ByteBuf, HuntingHornNoteMsg> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, HuntingHornNoteMsg::noteType,
            HuntingHornNoteMsg::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(HuntingHornNoteMsg message, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
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

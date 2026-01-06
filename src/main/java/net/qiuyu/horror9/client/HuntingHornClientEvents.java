package net.qiuyu.horror9.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.items.custom.HuntingHornItem;
import net.qiuyu.horror9.message.HuntingHornNoteMsg;

@Mod.EventBusSubscriber(modid = Horror9.MODID, value = Dist.CLIENT)
public class HuntingHornClientEvents {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof HuntingHornItem)) {
            stack = player.getOffhandItem();
            if (!(stack.getItem() instanceof HuntingHornItem)) return;
        }

        // 检测按键按下 (consumeClick 会在按下时返回一次 true)
        if (ModKeyBindings.CTRL_LEFT_CLICK_MAPPING.consumeClick()) {
            Horror9.sendMSGToServer(new HuntingHornNoteMsg(0)); // RED
        }
        if (ModKeyBindings.CTRL_RIGHT_CLICK_MAPPING.consumeClick()) {
            Horror9.sendMSGToServer(new HuntingHornNoteMsg(1)); // GREEN
        }
        if (ModKeyBindings.CTRL_MIDDLE_CLICK_MAPPING.consumeClick()) {
            Horror9.sendMSGToServer(new HuntingHornNoteMsg(2)); // BLUE
        }
    }
}

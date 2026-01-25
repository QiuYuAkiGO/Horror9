package net.qiuyu.horror9;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.qiuyu.horror9.client.ModKeyBindings;

@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public Player getClientSidePlayer() {
        return Minecraft.getInstance().player;
    }

    @Override
    public boolean isNoteKey1Down() {
        return ModKeyBindings.CTRL_LEFT_CLICK_MAPPING.isDown();
    }

    @Override
    public boolean isNoteKey2Down() {
        return ModKeyBindings.CTRL_RIGHT_CLICK_MAPPING.isDown();
    }

    @Override
    public boolean isNoteKey3Down() {
        return ModKeyBindings.CTRL_MIDDLE_CLICK_MAPPING.isDown();
    }

    @Override
    public void openCreatorPhoneScreen() {
        Minecraft.getInstance().setScreen(new net.qiuyu.horror9.client.gui.CreatorPhoneScreen());
    }
}

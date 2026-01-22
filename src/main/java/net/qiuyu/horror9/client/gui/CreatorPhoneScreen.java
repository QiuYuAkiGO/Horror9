package net.qiuyu.horror9.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.message.CreatorPhoneTeleportMsg;

import java.util.Collection;

public class CreatorPhoneScreen extends Screen {
    public CreatorPhoneScreen() {
        super(Component.translatable("gui.horror9.creator_phone.title"));
    }

    @Override
    protected void init() {
        int y = 40;
        if (Minecraft.getInstance().getConnection() != null) {
            Collection<PlayerInfo> players = Minecraft.getInstance().getConnection().getOnlinePlayers();
            for (PlayerInfo playerInfo : players) {
                String name = playerInfo.getProfile().getName();
                if (name.equals(Minecraft.getInstance().player.getGameProfile().getName())) continue;

                this.addRenderableWidget(Button.builder(Component.literal(name), (button) -> {
                    Horror9.sendMSGToServer(new CreatorPhoneTeleportMsg(name));
                    this.onClose();
                }).bounds(this.width / 2 - 100, y, 200, 20).build());
                y += 25;
            }
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        pGuiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

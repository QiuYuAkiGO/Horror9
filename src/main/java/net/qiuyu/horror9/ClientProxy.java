package net.qiuyu.horror9;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.qiuyu.horror9.client.ModKeyBindings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

import static net.qiuyu.horror9.Horror9.MODID;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public Player getClientSidePlayer() {
        return Minecraft.getInstance().player;
    }

    @Override
    public boolean isNoteKey1Down() {
        return ModKeyBindings.SHIFT_LEFT_CLICK_MAPPING.isDown();
    }

    @Override
    public boolean isNoteKey2Down() {
        return ModKeyBindings.SHIFT_RIGHT_CLICK_MAPPING.isDown();
    }

    @Override
    public boolean isNoteKey3Down() {
        return ModKeyBindings.SHIFT_MIDDLE_CLICK_MAPPING.isDown();
    }
}

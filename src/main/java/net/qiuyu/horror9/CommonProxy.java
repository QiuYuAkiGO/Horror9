package net.qiuyu.horror9;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;

import static net.qiuyu.horror9.Horror9.MODID;


@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonProxy {
    public Player getClientSidePlayer() {
        return null;
    }

    public boolean isNoteKeyFree() {
        return false;
    }

    public boolean isNoteKey1Down() {
        return false;
    }

    public boolean isNoteKey2Down() {
        return false;
    }

    public boolean isNoteKey3Down() {
        return false;
    }
}

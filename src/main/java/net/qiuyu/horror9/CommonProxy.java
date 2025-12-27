package net.qiuyu.horror9;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;

import static net.qiuyu.horror9.Horror9.MODID;


@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonProxy {
    public Player getClientSidePlayer() {
        return Minecraft.getInstance().player;
    }
}

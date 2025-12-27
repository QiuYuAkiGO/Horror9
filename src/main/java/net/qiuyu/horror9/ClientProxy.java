package net.qiuyu.horror9;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

import static net.qiuyu.horror9.Horror9.MODID;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class ClientProxy extends CommonProxy {

}

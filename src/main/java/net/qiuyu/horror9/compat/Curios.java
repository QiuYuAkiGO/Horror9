package net.qiuyu.horror9.compat;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.InterModComms;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotTypeMessage;

@SuppressWarnings({"removal", "UnstableApiUsage"})
public class Curios {
    public static String CHEST_SLOT = "chest";
    public static String HALO_SLOT = "halo";

    public static void registerCurioSlot(final String identifier, final int slots, final boolean isHidden, @Nullable final ResourceLocation icon) {
        final SlotTypeMessage.Builder message = new SlotTypeMessage.Builder(identifier);

        message.size(slots);

        if (isHidden) {
            message.hide();
        }

        if (icon != null) {
            message.icon(icon);
        }

        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, message::build);

    }
}


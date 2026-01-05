package net.qiuyu.horror9.armor.renderer.medicare;

import net.minecraft.resources.ResourceLocation;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.armor.custom.medicare.Medicare;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class MedicareRenderer extends GeoArmorRenderer<Medicare> {
    public MedicareRenderer() {
        super(new DefaultedItemGeoModel<>(ResourceLocation.parse(Horror9.MODID+ ":" + "armor/medicare")));
    }
}

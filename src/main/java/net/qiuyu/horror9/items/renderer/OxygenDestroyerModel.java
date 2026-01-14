package net.qiuyu.horror9.items.renderer;

import net.minecraft.resources.ResourceLocation;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.items.custom.OxygenDestroyerItem;
import software.bernie.geckolib.model.GeoModel;

public class OxygenDestroyerModel extends GeoModel<OxygenDestroyerItem> {
    @Override
    public ResourceLocation getModelResource(OxygenDestroyerItem animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "geo/item/oxygen_destroyer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(OxygenDestroyerItem animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "textures/item/oxygen_destroyer_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(OxygenDestroyerItem animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "animations/item/oxygen_destroyer.animation.json");
    }
}

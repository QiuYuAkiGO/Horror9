package net.qiuyu.horror9.items.renderer;

import net.minecraft.resources.ResourceLocation;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.items.custom.YuukaHaloItem;
import software.bernie.geckolib.model.GeoModel;

public class YuukaHaloModel extends GeoModel<YuukaHaloItem> {
    @Override
    public ResourceLocation getModelResource(YuukaHaloItem animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "geo/item/yuuka_halo.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(YuukaHaloItem animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "textures/item/yuuka_halo_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(YuukaHaloItem animatable) {
        // No animation for this one yet, returning null or a non-existent path is fine for Geckolib if not used
        return ResourceLocation.parse(Horror9.MODID + ":" + "animations/item/yuuka_halo.animation.json");
    }
}

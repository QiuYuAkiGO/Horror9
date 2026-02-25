package net.qiuyu.horror9.entity.renderer;

import net.minecraft.resources.ResourceLocation;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.entity.custom.BulculiterEntity;
import software.bernie.geckolib.model.GeoModel;

public class BulculiterModel extends GeoModel<BulculiterEntity> {
    @Override
    public ResourceLocation getModelResource(BulculiterEntity animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "geo/entity/bulculiter.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BulculiterEntity animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "textures/entity/bulculiter_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BulculiterEntity animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "animations/entity/bulculiter.animation.json");
    }
}

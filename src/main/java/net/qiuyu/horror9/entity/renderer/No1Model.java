package net.qiuyu.horror9.entity.renderer;

import net.minecraft.resources.ResourceLocation;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.entity.custom.No1Entity;
import software.bernie.geckolib.model.GeoModel;

public class No1Model extends GeoModel<No1Entity> {
    @Override
    public ResourceLocation getModelResource(No1Entity object) {
        return ResourceLocation.parse(Horror9.MODID+":"+"geo/no1.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(No1Entity object) {
        return ResourceLocation.parse(Horror9.MODID+":"+"textures/entity/no1_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(No1Entity animatable) {
        return ResourceLocation.parse(Horror9.MODID+":"+"animations/no1.animation.json");
    }
}

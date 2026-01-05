package net.qiuyu.horror9.entity.renderer;

import net.minecraft.resources.ResourceLocation;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.entity.custom.TheMistakenEntity;
import software.bernie.geckolib.model.GeoModel;

public class TheMistakenModel extends GeoModel<TheMistakenEntity> {
    @Override
    public ResourceLocation getModelResource(TheMistakenEntity animatable) {
        // 替换为实际的模型路径
        return ResourceLocation.parse(Horror9.MODID + ":" + "geo/entity/the_mistaken.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TheMistakenEntity animatable) {
        // 替换为实际的贴图路径
        return ResourceLocation.parse(Horror9.MODID + ":" + "textures/entity/the_mistaken_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TheMistakenEntity animatable) {
        // 替换为实际的动画路径
        return ResourceLocation.parse(Horror9.MODID + ":" + "animations/entity/the_mistaken.animation.json");
    }
}

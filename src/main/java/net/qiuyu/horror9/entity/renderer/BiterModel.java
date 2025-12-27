package net.qiuyu.horror9.entity.renderer;

import net.minecraft.resources.ResourceLocation;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.entity.custom.BiterEntity;
import software.bernie.geckolib.model.GeoModel;

public class BiterModel extends GeoModel<BiterEntity> {
    @Override
    public ResourceLocation getModelResource(BiterEntity animatable) {
        // 替换为实际的模型路径
        return ResourceLocation.parse(Horror9.MODID + ":" + "geo/biter.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BiterEntity animatable) {
        // 替换为实际的贴图路径
        return ResourceLocation.parse(Horror9.MODID + ":" + "textures/entity/biter_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BiterEntity animatable) {
        // 替换为实际的动画路径
        return ResourceLocation.parse(Horror9.MODID + ":" + "animations/biter.animation.json");
    }
}

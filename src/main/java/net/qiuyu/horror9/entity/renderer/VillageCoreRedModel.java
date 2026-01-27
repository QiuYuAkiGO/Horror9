package net.qiuyu.horror9.entity.renderer;

import net.minecraft.resources.ResourceLocation;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.entity.custom.VillageCoreRedEntity;
import software.bernie.geckolib.model.GeoModel;

public class VillageCoreRedModel extends GeoModel<VillageCoreRedEntity> {
    @Override
    public ResourceLocation getModelResource(VillageCoreRedEntity animatable) {
        // 替换为实际的模型路径
        return ResourceLocation.parse(Horror9.MODID + ":" + "geo/entity/village_core_red.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(VillageCoreRedEntity animatable) {
        // 替换为实际的贴图路径
        return ResourceLocation.parse(Horror9.MODID + ":" + "textures/entity/village_core_red_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(VillageCoreRedEntity animatable) {
        // 替换为实际的动画路径
        return ResourceLocation.parse(Horror9.MODID + ":" + "animations/entity/village_core_red.animation.json");
    }
}

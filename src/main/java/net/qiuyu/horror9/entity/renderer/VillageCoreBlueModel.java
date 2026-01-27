package net.qiuyu.horror9.entity.renderer;

import net.minecraft.resources.ResourceLocation;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.entity.custom.VillageCoreBlueEntity;
import net.qiuyu.horror9.entity.custom.VillageCoreEntity;
import software.bernie.geckolib.model.GeoModel;

public class VillageCoreBlueModel extends GeoModel<VillageCoreBlueEntity> {
    @Override
    public ResourceLocation getModelResource(VillageCoreBlueEntity animatable) {
        // 替换为实际的模型路径
        return ResourceLocation.parse(Horror9.MODID + ":" + "geo/entity/village_core_blue.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(VillageCoreBlueEntity animatable) {
        // 替换为实际的贴图路径
        return ResourceLocation.parse(Horror9.MODID + ":" + "textures/entity/village_core_blue_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(VillageCoreBlueEntity animatable) {
        // 替换为实际的动画路径
        return ResourceLocation.parse(Horror9.MODID + ":" + "animations/entity/village_core_blue.animation.json");
    }
}

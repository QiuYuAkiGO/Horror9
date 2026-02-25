package net.qiuyu.horror9.entity.renderer;

import net.minecraft.resources.ResourceLocation;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.entity.custom.ArthroeeEntity;
import software.bernie.geckolib.model.GeoModel;

public class ArthroeeModel extends GeoModel<ArthroeeEntity> {
    @Override
    public ResourceLocation getModelResource(ArthroeeEntity animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "geo/entity/arthroee.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ArthroeeEntity animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "textures/entity/arthroee_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ArthroeeEntity animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "animations/entity/arthroee.animation.json");
    }
}

package net.qiuyu.horror9.entity.renderer;

import net.minecraft.resources.ResourceLocation;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.entity.custom.WitherBombEntity;
import software.bernie.geckolib.model.GeoModel;

public class WitherBombEntityModel extends GeoModel<WitherBombEntity> {
    @Override
    public ResourceLocation getModelResource(WitherBombEntity animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "geo/item/wither_bomb.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WitherBombEntity animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "textures/item/wither_bomb_a_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WitherBombEntity animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "animations/item/wither_bomb.animation.json");
    }
}

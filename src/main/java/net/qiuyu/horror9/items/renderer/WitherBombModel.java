package net.qiuyu.horror9.items.renderer;

import net.minecraft.resources.ResourceLocation;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.items.custom.WitherBombItem;
import software.bernie.geckolib.model.GeoModel;

public class WitherBombModel extends GeoModel<WitherBombItem> {
    @Override
    public ResourceLocation getModelResource(WitherBombItem animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "geo/item/wither_bomb.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WitherBombItem animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "textures/item/wither_bomb_a_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WitherBombItem animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "animations/item/wither_bomb.animation.json");
    }
}

package net.qiuyu.horror9.items.renderer;

import net.minecraft.resources.ResourceLocation;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.items.custom.CreatorPhoneItem;
import software.bernie.geckolib.model.GeoModel;

public class CreatorPhoneModel extends GeoModel<CreatorPhoneItem> {
    @Override
    public ResourceLocation getModelResource(CreatorPhoneItem animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "geo/item/creator_phone.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CreatorPhoneItem animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "textures/item/creator_phone_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CreatorPhoneItem animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "animations/item/creator_phone.animation.json");
    }
}

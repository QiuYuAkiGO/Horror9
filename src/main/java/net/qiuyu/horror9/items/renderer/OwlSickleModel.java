package net.qiuyu.horror9.items.renderer;

import net.minecraft.resources.ResourceLocation;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.items.custom.OwlSickleItem;
import software.bernie.geckolib.model.GeoModel;

public class OwlSickleModel extends GeoModel<OwlSickleItem> {
    @Override
    public ResourceLocation getModelResource(OwlSickleItem animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "geo/item/owl_sickle.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(OwlSickleItem animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "textures/item/owl_sickle_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(OwlSickleItem animatable) {
        // 暂时没有相应文件,不要播放动画
        return ResourceLocation.parse(Horror9.MODID + ":" + "animations/item/owl_sickle.animation.json");
    }
}

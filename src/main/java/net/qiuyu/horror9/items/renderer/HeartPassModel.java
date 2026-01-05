package net.qiuyu.horror9.items.renderer;

import net.minecraft.resources.ResourceLocation;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.items.custom.HeartPassItem;
import net.qiuyu.horror9.items.custom.OwlSickleItem;
import software.bernie.geckolib.model.GeoModel;

public class HeartPassModel extends GeoModel<HeartPassItem> {
    @Override
    public ResourceLocation getModelResource(HeartPassItem animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "geo/heart_pass.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HeartPassItem animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "textures/item/heart_pass_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(HeartPassItem animatable) {
        // 暂时没有相应文件,不要播放动画
        return ResourceLocation.parse(Horror9.MODID + ":" + "animations/heart_pass.animation.json");
    }
}

package net.qiuyu.horror9.items.renderer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.items.custom.HeartPassItem;
import net.qiuyu.horror9.items.custom.OwlSickleItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class HeartPassRenderer extends GeoItemRenderer<HeartPassItem> {


    public HeartPassRenderer() {
        super(new HeartPassModel());
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull HeartPassItem animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "textures/item/heart_pass_texture.png");
    }

    @Override
    public RenderType getRenderType(HeartPassItem animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}

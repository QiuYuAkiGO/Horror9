package net.qiuyu.horror9.entity.renderer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.entity.custom.BiterEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BiterRenderer extends GeoEntityRenderer<BiterEntity> {
    public BiterRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BiterModel());
        this.shadowRadius = 0.5f; // 阴影半径
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BiterEntity animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "textures/entity/biter_texture.png");
    }

    @Override
    public RenderType getRenderType(BiterEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}

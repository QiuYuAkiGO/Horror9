package net.qiuyu.horror9.entity.renderer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.entity.custom.TheMistakenEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TheMistakenRenderer extends GeoEntityRenderer<TheMistakenEntity> {
    public TheMistakenRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TheMistakenModel());
        this.shadowRadius = 0.0f; // 阴影半径
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull TheMistakenEntity animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "textures/entity/the_mistaken_texture.png");
    }

    @Override
    public RenderType getRenderType(TheMistakenEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}

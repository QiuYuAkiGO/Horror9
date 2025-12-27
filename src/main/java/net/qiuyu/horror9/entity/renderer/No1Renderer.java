package net.qiuyu.horror9.entity.renderer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.entity.custom.No1Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class No1Renderer extends GeoEntityRenderer<No1Entity> {
    public No1Renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new No1Model());
        this.shadowRadius = 2.0f;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull No1Entity instance) {
        return ResourceLocation.parse(Horror9.MODID+":"+"textures/entity/no1_texture.png");
    }


    @Override
    public RenderType getRenderType(No1Entity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
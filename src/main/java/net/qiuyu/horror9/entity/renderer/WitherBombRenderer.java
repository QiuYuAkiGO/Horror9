package net.qiuyu.horror9.entity.renderer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.entity.custom.WitherBombEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WitherBombRenderer extends GeoEntityRenderer<WitherBombEntity> {
    public WitherBombRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new WitherBombEntityModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull WitherBombEntity animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "textures/item/wither_bomb_a_texture.png");
    }

    @Override
    public RenderType getRenderType(WitherBombEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}

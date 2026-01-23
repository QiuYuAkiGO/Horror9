package net.qiuyu.horror9.items.renderer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.items.custom.WitherBombItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class WitherBombRenderer extends GeoItemRenderer<WitherBombItem> {
    public WitherBombRenderer() {
        super(new WitherBombModel());
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull WitherBombItem animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "textures/item/wither_bomb_a_texture.png");
    }

    @Override
    public RenderType getRenderType(WitherBombItem animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}

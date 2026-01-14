package net.qiuyu.horror9.items.renderer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.items.custom.OxygenDestroyerItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class OxygenDestroyerRenderer extends GeoItemRenderer<OxygenDestroyerItem> {
    public OxygenDestroyerRenderer() {
        super(new OxygenDestroyerModel());
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull OxygenDestroyerItem animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "textures/item/oxygen_destoryer_texture.png");
    }

    @Override
    public RenderType getRenderType(OxygenDestroyerItem animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}

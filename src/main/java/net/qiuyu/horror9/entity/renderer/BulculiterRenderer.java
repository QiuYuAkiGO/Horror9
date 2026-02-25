package net.qiuyu.horror9.entity.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.entity.custom.BulculiterEntity;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BulculiterRenderer extends GeoEntityRenderer<BulculiterEntity> {
    public BulculiterRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BulculiterModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BulculiterEntity animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "textures/entity/bulculiter_texture.png");
    }
}

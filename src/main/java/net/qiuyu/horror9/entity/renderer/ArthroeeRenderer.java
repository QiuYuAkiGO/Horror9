package net.qiuyu.horror9.entity.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.entity.custom.ArthroeeEntity;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ArthroeeRenderer extends GeoEntityRenderer<ArthroeeEntity> {
    public ArthroeeRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ArthroeeModel());
        this.shadowRadius = 0.6f;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ArthroeeEntity animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "textures/entity/arthroee_texture.png");
    }
}

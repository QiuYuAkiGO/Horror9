package net.qiuyu.horror9.entity.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownTridentRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.qiuyu.horror9.Horror9;

public class NullTridentEntityRenderer extends ThrownTridentRenderer {
    public static final ResourceLocation TEXTURE = ResourceLocation.parse(Horror9.MODID + ":textures/entity/null_trident_texture.png");

    public NullTridentEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(ThrownTrident entity) {
        return TEXTURE;
    }
}

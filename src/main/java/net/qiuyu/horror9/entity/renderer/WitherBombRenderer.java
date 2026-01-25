package net.qiuyu.horror9.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
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
    public void render(@NotNull WitherBombEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        // 缩放以匹配物品大小
        poseStack.scale(0.5f, 0.5f, 0.5f);
        // 调整位置使模型中心与实体中心对齐
        poseStack.translate(0, -0.5f, 0);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.popPose();
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

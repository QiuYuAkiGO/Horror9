package net.qiuyu.horror9.items.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.items.custom.YuukaHaloItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class YuukaHaloRenderer extends GeoItemRenderer<YuukaHaloItem> implements ICurioRenderer {

    public YuukaHaloRenderer() {
        super(new YuukaHaloModel());
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(
            ItemStack stack,
            SlotContext slotContext,
            PoseStack matrixStack,
            RenderLayerParent<T, M> renderLayerParent,
            MultiBufferSource renderTypeBuffer,
            int light,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch) {

        if (renderLayerParent.getModel() instanceof HumanoidModel<?> model) {
            matrixStack.pushPose();
            // Apply Head Transform (Body + Head)
            model.head.translateAndRotate(matrixStack);

            // Apply Halo Offsets (Local to Head)
            // Position offset: Up 1.7 blocks, Forward 0.75 blocks (relative to head)
            matrixStack.translate(0.0D, -1.7D, -0.75D);

            // Bobbing effect
            double floatOffset = Math.sin((ageInTicks + partialTicks) * 0.1) * 0.05;
            matrixStack.translate(0.0D, floatOffset, 0.0D);

            // Rotation offset: Tilt back 15 degrees
            matrixStack.mulPose(Axis.XP.rotationDegrees(-15.0F));

            // Render
            super.renderByItem(stack, ItemDisplayContext.FIXED, matrixStack, renderTypeBuffer, light, OverlayTexture.NO_OVERLAY);

            matrixStack.popPose();
        }
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull YuukaHaloItem animatable) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "textures/item/yuuka_halo_texture.png");
    }

    @Override
    public RenderType getRenderType(YuukaHaloItem animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutout(texture);
    }
}

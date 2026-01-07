package net.qiuyu.horror9.items.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class HeartMetalRenderer implements ICurioRenderer {
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
            // 绑定到身体模型的部分，使渲染跟随身体动作（如潜行、走动等）
            model.body.translateAndRotate(matrixStack);
            // 1.0 通常对应 1/16 块。
            // x: 负值向右, y: 正值向下, z: 负值向前
            double zOffset = -0.15D;
            if (!slotContext.entity().getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
                zOffset = -0.20D;
            }
            matrixStack.translate(-0.15D, 0.25D, zOffset);
            matrixStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
            matrixStack.scale(0.3F, 0.3F, 0.3F);
            Minecraft.getInstance().getItemRenderer().renderStatic(
                    stack,
                    ItemDisplayContext.FIXED,
                    light,
                    OverlayTexture.NO_OVERLAY,
                    matrixStack,
                    renderTypeBuffer,
                    slotContext.entity().level(),
                    0
            );
            matrixStack.popPose();
        }
    }
}

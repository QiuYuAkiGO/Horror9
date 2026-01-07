package net.qiuyu.horror9.items.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
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

            // 调整位置到右侧胸部
            // Minecraft 模型坐标单位中，1.0 通常对应 1/16 块。
            // 这里 translate 的参数是米（块）。
            // x: 负值向右, y: 正值向下, z: 负值向前
            matrixStack.translate(-0.15D, 0.25D, -0.15D);

            // 缩小物品大小，适应胸部饰品
            matrixStack.scale(0.5F, 0.5F, 0.5F);

            // 渲染物品
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

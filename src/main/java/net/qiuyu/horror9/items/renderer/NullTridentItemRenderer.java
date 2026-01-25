package net.qiuyu.horror9.items.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.qiuyu.horror9.Horror9;

public class NullTridentItemRenderer extends BlockEntityWithoutLevelRenderer {
    private static final ResourceLocation TRIDENT_LOCATION = ResourceLocation.parse(Horror9.MODID + ":textures/entity/null_trident_texture.png");
    private final TridentModel tridentModel;

    public NullTridentItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        this.tridentModel = new TridentModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.TRIDENT));
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        if (displayContext == ItemDisplayContext.GUI) {
            poseStack.pushPose();
//            poseStack.scale(2.0F, 2.0F, 2.0F);
            BakedModel model = Minecraft.getInstance().getModelManager().getModel(new ModelResourceLocation(ResourceLocation.parse(Horror9.MODID + ":item/null_trident_2d"), "inventory"));
            Minecraft.getInstance().getItemRenderer().render(stack, displayContext, false, poseStack, buffer, packedLight, packedOverlay, model);
            poseStack.popPose();
            return;
        }

        poseStack.pushPose();
        poseStack.scale(1.0F, -1.0F, -1.0F);
        VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(buffer, this.tridentModel.renderType(TRIDENT_LOCATION), false, stack.hasFoil());
        this.tridentModel.renderToBuffer(poseStack, vertexconsumer, packedLight, packedOverlay, 0xFFFFFFFF);
        poseStack.popPose();
    }
}

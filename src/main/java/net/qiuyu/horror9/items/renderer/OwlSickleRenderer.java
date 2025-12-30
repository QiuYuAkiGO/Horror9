//package net.qiuyu.horror9.items.renderer;
//
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.RenderType;
//import net.minecraft.resources.ResourceLocation;
//import net.qiuyu.horror9.Horror9;
//import net.qiuyu.horror9.items.custom.OwlSickleItem;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//import software.bernie.geckolib.model.GeoModel;
//import software.bernie.geckolib.renderer.GeoItemRenderer;
//
//public class OwlSickleRenderer extends GeoItemRenderer<OwlSickleItem> {
//
//
//    public OwlSickleRenderer(GeoModel<OwlSickleItem> model) {
//        super(model);
//    }
//
//    @Override
//    public @NotNull ResourceLocation getTextureLocation(@NotNull OwlSickleItem animatable) {
//        return ResourceLocation.parse(Horror9.MODID + ":" + "textures/entity/owl_sickle.png");
//    }
//
//    @Override
//    public RenderType getRenderType(OwlSickleItem animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
//        return super.getRenderType(animatable, texture, bufferSource, partialTick);
//    }
//}

package net.qiuyu.horror9.items.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.qiuyu.horror9.items.renderer.HeartPassRenderer;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

public class HeartPassItem extends AxeItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public HeartPassItem(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pProperties.attributes(AxeItem.createAttributes(pTier, pAttackDamageModifier, pAttackSpeedModifier)));
    }

    public static Properties createDefaultProperties() {
        return new Properties().durability(368);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.horror9.heart_pass.line1").withStyle(ChatFormatting.LIGHT_PURPLE));
        pTooltipComponents.add(Component.translatable("tooltip.horror9.heart_pass.line2").withStyle(ChatFormatting.DARK_RED).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private HeartPassRenderer renderer;
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null){
                    this.renderer = new HeartPassRenderer();
                }
                return this.renderer;
            }
        });
    }
}

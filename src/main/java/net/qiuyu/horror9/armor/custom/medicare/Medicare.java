package net.qiuyu.horror9.armor.custom.medicare;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.qiuyu.horror9.Horror9;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Medicare extends ArmorItem  {
//    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final Map<ArmorItem.Type, Double> HEALTH_BONUS_MAP = Util.make(new EnumMap<>(ArmorItem.Type.class), (bonus) -> {
        bonus.put(ArmorItem.Type.BOOTS, 4.0D);
        bonus.put(ArmorItem.Type.LEGGINGS, 7.0D);
        bonus.put(ArmorItem.Type.CHESTPLATE, 8.0D);
        bonus.put(ArmorItem.Type.HELMET, 5.0D);
    });

    public Medicare(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        ItemAttributeModifiers modifiers = super.getDefaultAttributeModifiers(stack);
        double healthBonus = HEALTH_BONUS_MAP.getOrDefault(this.type, 0.0D);
        if (healthBonus > 0) {
            modifiers = modifiers.withModifierAdded(
                    Attributes.MAX_HEALTH,
                    new AttributeModifier(ResourceLocation.parse(Horror9.MODID + ":medicare_health_bonus_" + this.type.getSerializedName()), healthBonus, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.bySlot(this.type.getSlot())
            );
        }
        return modifiers;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.horror9.medicare.line1").withStyle(ChatFormatting.LIGHT_PURPLE));
        tooltipComponents.add(Component.translatable("tooltip.horror9.medicare.line2").withStyle(ChatFormatting.LIGHT_PURPLE));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}

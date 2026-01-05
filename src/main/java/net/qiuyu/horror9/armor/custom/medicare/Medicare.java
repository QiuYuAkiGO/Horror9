package net.qiuyu.horror9.armor.custom.medicare;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Medicare extends ArmorItem  {
//    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final Map<ArmorItem.Type, Double> HEALTH_BONUS_MAP = Util.make(new EnumMap<>(ArmorItem.Type.class), (bonus) -> {
        bonus.put(ArmorItem.Type.BOOTS, 2.0D);
        bonus.put(ArmorItem.Type.LEGGINGS, 7.0D);
        bonus.put(ArmorItem.Type.CHESTPLATE, 8.0D);
        bonus.put(ArmorItem.Type.HELMET, 3.0D);
    });

    private static final UUID[] HEALTH_MODIFIER_UUID_PER_SLOT = new UUID[]{
            UUID.fromString("7842b57d-e641-4da3-b441-a3a2341d7254"),
            UUID.fromString("c5963b61-4682-411c-9602-544158428574"),
            UUID.fromString("d9426f0f-08e8-466d-96f7-921360662d0d"),
            UUID.fromString("747206ca-9781-4328-8d00-47b744a87b54")
    };

    public Medicare(ArmorMaterial pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        if (pEquipmentSlot == this.type.getSlot()) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getDefaultAttributeModifiers(pEquipmentSlot));
            double healthBonus = HEALTH_BONUS_MAP.getOrDefault(this.type, 0.0D);
            builder.put(Attributes.MAX_HEALTH, new AttributeModifier(HEALTH_MODIFIER_UUID_PER_SLOT[pEquipmentSlot.getIndex()], "Max Health modifier", healthBonus, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.horror9.medicare.line1").withStyle(ChatFormatting.LIGHT_PURPLE));
        pTooltipComponents.add(Component.translatable("tooltip.horror9.medicare.line2").withStyle(ChatFormatting.LIGHT_PURPLE));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}

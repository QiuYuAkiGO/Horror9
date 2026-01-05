package net.qiuyu.horror9.armor.custom.medicare;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.Util;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.qiuyu.horror9.armor.renderer.medicare.MedicareRenderer;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

public class Medicare extends ArmorItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final Map<ArmorItem.Type, Double> HEALTH_BONUS_MAP = Util.make(new EnumMap<>(ArmorItem.Type.class), (bonus) -> {
        bonus.put(ArmorItem.Type.BOOTS, 2.0D);
        bonus.put(ArmorItem.Type.LEGGINGS, 7.0D);
        bonus.put(ArmorItem.Type.CHESTPLATE, 8.0D);
        bonus.put(ArmorItem.Type.HELMET, 3.0D);
    });

    private static final UUID[] ARMOR_MODIFIER_UUID_PER_SLOT = new UUID[]{
            UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B20"),
            UUID.fromString("D8499B04-0E66-4726-8529-A9AA043017ad"),
            UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
            UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD921BB150")
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
            builder.put(Attributes.MAX_HEALTH, new AttributeModifier(ARMOR_MODIFIER_UUID_PER_SLOT[pEquipmentSlot.getIndex()], "Max Health modifier", healthBonus, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, 20, state -> {
            state.setAnimation(DefaultAnimations.IDLE);
            Entity entity = state.getData(DataTickets.ENTITY);

            if (entity instanceof ArmorStand){
                return PlayState.CONTINUE;
            }

            Set<Item> wornArmor = new ObjectOpenHashSet<>();

            for (ItemStack stack : entity.getArmorSlots()) {
                if (stack.isEmpty()){
                    return PlayState.STOP;
                }
                wornArmor.add(stack.getItem());
            }

            return PlayState.CONTINUE;
        }));
    }



    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null){
                    this.renderer = new MedicareRenderer();
                }
                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

                return this.renderer;
            }
        });
    }


}

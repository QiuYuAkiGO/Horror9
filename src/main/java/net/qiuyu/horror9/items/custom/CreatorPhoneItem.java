package net.qiuyu.horror9.items.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.qiuyu.horror9.items.renderer.CreatorPhoneRenderer;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class CreatorPhoneItem extends Item implements GeoItem {
    public static final int MAX_ENERGY = 100;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public CreatorPhoneItem(Properties pProperties) {
        super(pProperties.stacksTo(1));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pHand) {
        if (pLevel.isClientSide) {
            openScreen();
        }
        return InteractionResultHolder.sidedSuccess(pPlayer.getItemInHand(pHand), pLevel.isClientSide());
    }

    private void openScreen() {
        net.qiuyu.horror9.Horror9.PROXY.openCreatorPhoneScreen();
    }

    @Override
    public void inventoryTick(@NotNull ItemStack pStack, Level pLevel, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (!pLevel.isClientSide && pEntity instanceof Player) {
            if (pLevel.getGameTime() % 20 == 0) {
                IEnergyStorage energy = pStack.getCapability(Capabilities.EnergyStorage.ITEM);
                if (energy instanceof ItemEnergyStorage itemEnergy) {
                    itemEnergy.receiveEnergyInternal(5, false);
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        IEnergyStorage energy = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (energy != null) {
            int percentage = (int) ((float) energy.getEnergyStored() / energy.getMaxEnergyStored() * 100);
            tooltipComponents.add(Component.translatable("tooltip.horror9.creator_phone.energy", percentage)
                    .withStyle(ChatFormatting.GOLD));
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
    @Override
    public boolean isBarVisible(@NotNull ItemStack pStack) {
        return true;
    }

    @Override
    public int getBarColor(@NotNull ItemStack pStack) {
        return 0xFF00FF00;
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        IEnergyStorage energy = pStack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (energy != null) {
            return Math.round((float) energy.getEnergyStored() * 13.0F / (float) energy.getMaxEnergyStored());
        }
        return 0;
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
            private CreatorPhoneRenderer renderer;
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null){
                    this.renderer = new CreatorPhoneRenderer();
                }
                return this.renderer;
            }
        });
    }

    public static class ItemEnergyStorage extends EnergyStorage {
        private final ItemStack stack;

        public ItemEnergyStorage(ItemStack stack, int capacity) {
            super(capacity);
            this.stack = stack;
            CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
            if (tag.contains("Energy")) {
                this.energy = tag.getInt("Energy");
            }
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            return 0;
        }

        @Override
        public boolean canReceive() {
            return false;
        }

        public void receiveEnergyInternal(int maxReceive, boolean simulate) {
            int energyStored = getEnergyStored();
            int energyReceived = Math.min(capacity - energyStored, maxReceive);
            if (!simulate && energyReceived != 0) {
                this.energy = energyStored + energyReceived;
                CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
                tag.putInt("Energy", this.energy);
                stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
            }
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            int energyStored = getEnergyStored();
            int energyExtracted = Math.min(energyStored, maxExtract);
            if (!simulate && energyExtracted != 0) {
                this.energy = energyStored - energyExtracted;
                CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
                tag.putInt("Energy", this.energy);
                stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
            }
            return energyExtracted;
        }

        @Override
        public int getEnergyStored() {
            CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
            if (tag.contains("Energy")) {
                this.energy = tag.getInt("Energy");
            }
            return this.energy;
        }
    }
}

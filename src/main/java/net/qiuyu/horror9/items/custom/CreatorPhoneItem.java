package net.qiuyu.horror9.items.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CreatorPhoneItem extends Item {
    public static final int MAX_ENERGY = 100;

    public CreatorPhoneItem(Properties pProperties) {
        super(pProperties.stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        if (pLevel.isClientSide) {
            openScreen();
        }
        return InteractionResultHolder.sidedSuccess(pPlayer.getItemInHand(pHand), pLevel.isClientSide());
    }

    private void openScreen() {
        // Use reflection or a proxy to avoid server-side class loading issues if needed, 
        // but since this is called only if isClientSide is true, it's generally safe 
        // as long as the method itself doesn't have Screen in its signature or body in a way that triggers loading.
        // Actually, it's better to use a DistExecutor or similar if it's in a common class.
        net.qiuyu.horror9.Horror9.PROXY.openCreatorPhoneScreen();
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (!pLevel.isClientSide && pEntity instanceof Player) {
            if (pLevel.getGameTime() % 20 == 0) {
                pStack.getCapability(ForgeCapabilities.ENERGY).ifPresent(energy -> {
                    if (energy instanceof ItemEnergyStorage itemEnergy) {
                        itemEnergy.receiveEnergyInternal(5, false);
                    }
                });
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pStack.getCapability(ForgeCapabilities.ENERGY).ifPresent(energy -> {
            int percentage = (int) ((float) energy.getEnergyStored() / energy.getMaxEnergyStored() * 100);
            pTooltipComponents.add(Component.translatable("tooltip.horror9.creator_phone.energy", percentage)
                    .withStyle(ChatFormatting.GOLD));
        });
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return true;
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        return 0xFF00FF00;
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        return pStack.getCapability(ForgeCapabilities.ENERGY)
                .map(e -> Math.round((float) e.getEnergyStored() * 13.0F / (float) e.getMaxEnergyStored()))
                .orElse(0);
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CreatorPhoneEnergyCapability(stack);
    }

    private static class CreatorPhoneEnergyCapability implements ICapabilityProvider {
        private final LazyOptional<IEnergyStorage> energy;

        public CreatorPhoneEnergyCapability(ItemStack stack) {
            this.energy = LazyOptional.of(() -> new ItemEnergyStorage(stack, MAX_ENERGY));
        }

        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable net.minecraft.core.Direction side) {
            return ForgeCapabilities.ENERGY.orEmpty(cap, energy);
        }
    }

    private static class ItemEnergyStorage extends EnergyStorage {
        private final ItemStack stack;

        public ItemEnergyStorage(ItemStack stack, int capacity) {
            super(capacity);
            this.stack = stack;
            if (stack.hasTag() && stack.getTag().contains("Energy")) {
                this.energy = stack.getTag().getInt("Energy");
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
                stack.getOrCreateTag().putInt("Energy", this.energy);
            }
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            int energyStored = getEnergyStored();
            int energyExtracted = Math.min(energyStored, maxExtract);
            if (!simulate && energyExtracted != 0) {
                this.energy = energyStored - energyExtracted;
                stack.getOrCreateTag().putInt("Energy", this.energy);
            }
            return energyExtracted;
        }

        @Override
        public int getEnergyStored() {
            if (stack.hasTag() && stack.getTag().contains("Energy")) {
                this.energy = stack.getTag().getInt("Energy");
            }
            return this.energy;
        }
    }
}

package net.qiuyu.horror9.register;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.qiuyu.horror9.Horror9;

import static net.qiuyu.horror9.register.ModBlocks.*;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Horror9.MODID);


    public static BlockItem register_block(Block block) {
        return new BlockItem(block, new Item.Properties());
    }

    public static Item register_item() {
        return new Item(new Item.Properties());
    }


}

package net.qiuyu.horror9.entity.item;


import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.block.ModBlocks;
import net.qiuyu.horror9.entity.ModEntityTypes;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Horror9.MODID);


    public static final RegistryObject<Item> NO1_SPAWN_EGG = ITEMS.register("no1_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.NO1, 0x22b341, 0x19732e,
                    new Item.Properties()));

    // Corruption Wood Items
    public static final RegistryObject<Item> CORRUPTION_LOG = ITEMS.register("corruption_log",
            () -> new BlockItem(ModBlocks.CORRUPTION_LOG.get(), new Item.Properties()));

    public static final RegistryObject<Item> CORRUPTION_WOOD = ITEMS.register("corruption_wood",
            () -> new BlockItem(ModBlocks.CORRUPTION_WOOD.get(), new Item.Properties()));

    public static final RegistryObject<Item> CORRUPTION_STRIPPED_LOG = ITEMS.register("corruption_stripped_log",
            () -> new BlockItem(ModBlocks.CORRUPTION_STRIPPED_LOG.get(), new Item.Properties()));

    public static final RegistryObject<Item> CORRUPTION_STRIPPED_WOOD = ITEMS.register("corruption_stripped_wood",
            () -> new BlockItem(ModBlocks.CORRUPTION_STRIPPED_WOOD.get(), new Item.Properties()));

    public static final RegistryObject<Item> CORRUPTION_PLANKS = ITEMS.register("corruption_planks",
            () -> new BlockItem(ModBlocks.CORRUPTION_PLANKS.get(), new Item.Properties()));

    public static final RegistryObject<Item> CORRUPTION_LEAVES = ITEMS.register("corruption_leaves",
            () -> new BlockItem(ModBlocks.CORRUPTION_LEAVES.get(), new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

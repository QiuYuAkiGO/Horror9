package net.qiuyu.horror9.entity.item;


import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.entity.ModEntityTypes;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Horror9.MODID);


    public static final RegistryObject<Item> NO1_SPAWN_EGG = ITEMS.register("no1_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.NO1, 0x22b341, 0x19732e,
                    new Item.Properties()));
    public static final RegistryObject<Item> BITER_SPAWN_EGG = ITEMS.register("biter_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.BITER, 0x22b341, 0x19732e,
                    new Item.Properties()));
    public static final RegistryObject<Item> THE_MISTAKEN_SPAWN_EGG = ITEMS.register("the_mistaken_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.THE_MISTAKEN, 0x22b341, 0x19732e,
                    new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

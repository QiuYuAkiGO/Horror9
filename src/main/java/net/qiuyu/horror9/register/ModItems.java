package net.qiuyu.horror9.register;


import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.armor.ModArmorMaterials;
import net.qiuyu.horror9.armor.custom.medicare.Medicare;
import net.qiuyu.horror9.entity.ModEntityTypes;
import net.qiuyu.horror9.items.custom.HeartMetal;
import net.qiuyu.horror9.items.custom.HeartPassItem;
import net.qiuyu.horror9.items.custom.NullTridentItem;
import net.qiuyu.horror9.items.custom.OwlSickleItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Horror9.MODID);

    public static final RegistryObject<Item> NULL_TRIDENT = ITEMS.register("null_trident",
            () -> new NullTridentItem(new Item.Properties().stacksTo(1).durability(250)));

    public static final RegistryObject<Item> NO1_SPAWN_EGG = ITEMS.register("no1_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.NO1, 0x22b341, 0x19732e,
                    new Item.Properties()));
    public static final RegistryObject<Item> BITER_SPAWN_EGG = ITEMS.register("biter_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.BITER, 0x22b341, 0x19732e,
                    new Item.Properties()));
    public static final RegistryObject<Item> THE_MISTAKEN_SPAWN_EGG = ITEMS.register("the_mistaken_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.THE_MISTAKEN, 0x22b341, 0x19732e,
                    new Item.Properties()));
    public static final RegistryObject<Item> OWL_SICKLE = ITEMS.register("owl_sickle", () -> new OwlSickleItem(Tiers.NETHERITE, 9, -3.84f,
            new Item.Properties().durability(198)));
    public static final RegistryObject<Item> HEART_METAL = ITEMS.register("heart_metal", () -> new HeartMetal(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> HEART_PASS = ITEMS.register("heart_pass", () -> new HeartPassItem(Tiers.DIAMOND, 12, -3.1f,
            HeartPassItem.createDefaultProperties()));
    public static final RegistryObject<ArmorItem> MEDICARE_HELMET = ITEMS.register("medicare_helmet", () -> new Medicare(ModArmorMaterials.MEDICARE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<ArmorItem> MEDICARE_CHESTPLATE = ITEMS.register("medicare_chestplate", () -> new Medicare(ModArmorMaterials.MEDICARE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<ArmorItem> MEDICARE_LEGGINGS = ITEMS.register("medicare_leggings", () -> new Medicare(ModArmorMaterials.MEDICARE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<ArmorItem> MEDICARE_BOOTS = ITEMS.register("medicare_boots", () -> new Medicare(ModArmorMaterials.MEDICARE, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

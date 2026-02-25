package net.qiuyu.horror9.register;

import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.armor.ModArmorMaterials;
import net.qiuyu.horror9.armor.custom.medicare.Medicare;
import net.qiuyu.horror9.entity.ModEntityTypes;
import net.qiuyu.horror9.items.custom.*;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(Horror9.MODID);

    public static final DeferredItem<Item> NULL_TRIDENT = ITEMS.register("null_trident",
            () -> new NullTridentItem(new Item.Properties().stacksTo(1).durability(250)));

    public static final DeferredItem<Item> NO1_SPAWN_EGG = ITEMS.register("no1_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntityTypes.NO1, 0x22b341, 0x19732e,
                    new Item.Properties()));
    public static final DeferredItem<Item> BITER_SPAWN_EGG = ITEMS.register("biter_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntityTypes.BITER, 0x22b341, 0x19732e,
                    new Item.Properties()));
    public static final DeferredItem<Item> THE_MISTAKEN_SPAWN_EGG = ITEMS.register("the_mistaken_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntityTypes.THE_MISTAKEN, 0x22b341, 0x19732e,
                    new Item.Properties()));
    public static final DeferredItem<Item> OWL_SICKLE = ITEMS.register("owl_sickle", () -> new OwlSickleItem(Tiers.NETHERITE, 9, -3.84f,
            new Item.Properties().durability(198)));
    public static final DeferredItem<Item> HEART_METAL = ITEMS.register("heart_metal", () -> new HeartMetal(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> HEART_PASS = ITEMS.register("heart_pass", () -> new HeartPassItem(Tiers.DIAMOND, 12, -3.1f,
            HeartPassItem.createDefaultProperties()));
    public static final DeferredItem<Item> OXYGEN_DESTROYER = ITEMS.register("oxygen_destroyer", () -> new OxygenDestroyerItem(Tiers.DIAMOND, 20, -3.1f,
            OxygenDestroyerItem.createDefaultProperties()));
    public static final DeferredItem<ArmorItem> MEDICARE_HELMET = ITEMS.register("medicare_helmet", () -> new Medicare(ModArmorMaterials.MEDICARE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final DeferredItem<ArmorItem> MEDICARE_CHESTPLATE = ITEMS.register("medicare_chestplate", () -> new Medicare(ModArmorMaterials.MEDICARE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final DeferredItem<ArmorItem> MEDICARE_LEGGINGS = ITEMS.register("medicare_leggings", () -> new Medicare(ModArmorMaterials.MEDICARE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final DeferredItem<ArmorItem> MEDICARE_BOOTS = ITEMS.register("medicare_boots", () -> new Medicare(ModArmorMaterials.MEDICARE, ArmorItem.Type.BOOTS, new Item.Properties()));
    public static final DeferredItem<Item> CREATOR_PHONE = ITEMS.register("creator_phone", () -> new CreatorPhoneItem(new Item.Properties()));
    public static final DeferredItem<Item> YUUKA_HALO = ITEMS.register("yuuka_halo", () -> new YuukaHaloItem(new Item.Properties()));
    public static final DeferredItem<Item> PROTECT_STONE = ITEMS.register("protect_stone", () -> new ProtectStoneItem(new Item.Properties().stacksTo(1)));

    // 凋零炸弹
    public static final DeferredItem<WitherBombItem> WITHER_BOMB = ITEMS.register("wither_bomb", () -> new WitherBombItem(new Item.Properties()));
    // 钻石剑
    public static final DeferredItem<Item> OLD_DIAMOND_SWORD = ITEMS.register(
            "old_diamond_sword", () -> new OldSwordItem(Tiers.DIAMOND, new Item.Properties().attributes(OldSwordItem.createAttributes(Tiers.DIAMOND, 3)), 3)
    );
    // 村庄核心
    public static final DeferredItem<Item> VILLAGE_CORE_RED_SPAWN_EGG = ITEMS.register("village_core_red_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntityTypes.VILLAGE_CORE_RED, 0xF8F8FF, 0xFF0000,
                    new Item.Properties()));
    public static final DeferredItem<Item> VILLAGE_CORE_BLUE_SPAWN_EGG = ITEMS.register("village_core_blue_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntityTypes.VILLAGE_CORE_BLUE, 0xF8F8FF, 0x0000FF,
                    new Item.Properties()));

    // Corruption basic materials
    public static final DeferredItem<Item> CORRUPTED_BRAIN = ITEMS.register("corrupted_brain",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CORRUPTED_CHITIN = ITEMS.register("corrupted_chitin",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CORRUPTED_STICK = ITEMS.register("corrupted_stick",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CORRUPTED_FOREGUT = ITEMS.register("corrupted_foregut",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> DARK_SOUL = ITEMS.register("dark_soul",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> COTTON = ITEMS.register("cotton",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> VEIN = ITEMS.register("vein",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> TOXIC_FLAME_CRYSTALLIZATION = ITEMS.register("toxic_flame_crystallization",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> WATER_LAVA_CRYSTALLIZATION = ITEMS.register("water_lava_crystallization",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> NITROCELLULOSE = ITEMS.register("nitrocellulose",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> TOOTH = ITEMS.register("tooth",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BOTTLE_OF_TOXIC_FLAME = ITEMS.register("bottle_of_toxic_flame",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CORRECTLY_SNOW_BALL = ITEMS.register("correctly_snow_ball",
            () -> new Item(new Item.Properties()));

    // Corruption weapons & tools
    public static final DeferredItem<Item> CORRUPTLY_LAVA_SNOW_BROAD_SWORD = ITEMS.register("corruptly_lava_snow_broad_sword",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CORRUPTLY_LAVA_SNOW_KNIFE = ITEMS.register("corruptly_lava_snow_knife",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CORRUPTLY_TOXIC_FLAME_SWORD = ITEMS.register("corruptly_toxic_flame_sword",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SAND_WATER_THROWING_KNIFE = ITEMS.register("sand_water_throwing_knife",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<BowItem> CORRUPTLY_TOXIC_FLAME_BOW = ITEMS.register("corruptly_toxic_flame_bow",
            () -> new BowItem(new Item.Properties().durability(384)));

    // Corruption armor (heavy/light, lava/snow, sand/water, toxic flame)
    public static final DeferredItem<ArmorItem> CORRUPTLY_LAVA_SNOW_HEAVY_HELMET = ITEMS.register("corruptly_lava_snow_heavy_helmet",
            () -> new ArmorItem(ModArmorMaterials.MEDICARE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final DeferredItem<ArmorItem> CORRUPTLY_LAVA_SNOW_HEAVY_CHESTPLATE = ITEMS.register("corruptly_lava_snow_heavy_chestplate",
            () -> new ArmorItem(ModArmorMaterials.MEDICARE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final DeferredItem<ArmorItem> CORRUPTLY_LAVA_SNOW_HEAVY_LEGGINGS = ITEMS.register("corruptly_lava_snow_heavy_leggings",
            () -> new ArmorItem(ModArmorMaterials.MEDICARE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final DeferredItem<ArmorItem> CORRUPTLY_LAVA_SNOW_HEAVY_BOOTS = ITEMS.register("corruptly_lava_snow_heavy_boots",
            () -> new ArmorItem(ModArmorMaterials.MEDICARE, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final DeferredItem<ArmorItem> CORRUPTLY_SAND_WATER_LIGHT_HELMET = ITEMS.register("corruptly_sand_water_light_helmet",
            () -> new ArmorItem(ModArmorMaterials.MEDICARE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final DeferredItem<ArmorItem> CORRUPTLY_SAND_WATER_LIGHT_CHESTPLATE = ITEMS.register("corruptly_sand_water_light_chestplate",
            () -> new ArmorItem(ModArmorMaterials.MEDICARE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final DeferredItem<ArmorItem> CORRUPTLY_SAND_WATER_LIGHT_LEGGINGS = ITEMS.register("corruptly_sand_water_light_leggings",
            () -> new ArmorItem(ModArmorMaterials.MEDICARE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final DeferredItem<ArmorItem> CORRUPTLY_SAND_WATER_LIGHT_BOOTS = ITEMS.register("corruptly_sand_water_light_boots",
            () -> new ArmorItem(ModArmorMaterials.MEDICARE, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final DeferredItem<ArmorItem> CORRUPTLY_TOXIC_FLAME_HEAVY_HELMET = ITEMS.register("corruptly_toxic_flame_heavy_helmet",
            () -> new ArmorItem(ModArmorMaterials.MEDICARE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final DeferredItem<ArmorItem> CORRUPTLY_TOXIC_FLAME_HEAVY_CHESTPLATE = ITEMS.register("corruptly_toxic_flame_heavy_chestplate",
            () -> new ArmorItem(ModArmorMaterials.MEDICARE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final DeferredItem<ArmorItem> CORRUPTLY_TOXIC_FLAME_HEAVY_LEGGINGS = ITEMS.register("corruptly_toxic_flame_heavy_leggings",
            () -> new ArmorItem(ModArmorMaterials.MEDICARE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final DeferredItem<ArmorItem> CORRUPTLY_TOXIC_FLAME_HEAVY_BOOTS = ITEMS.register("corruptly_toxic_flame_heavy_boots",
            () -> new ArmorItem(ModArmorMaterials.MEDICARE, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final DeferredItem<ArmorItem> CORRUPTLY_TOXIC_FLAME_LIGHT_HELMET = ITEMS.register("corruptly_toxic_flame_light_helmet",
            () -> new ArmorItem(ModArmorMaterials.MEDICARE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final DeferredItem<ArmorItem> CORRUPTLY_TOXIC_FLAME_LIGHT_CHESTPLATE = ITEMS.register("corruptly_toxic_flame_light_chestplate",
            () -> new ArmorItem(ModArmorMaterials.MEDICARE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final DeferredItem<ArmorItem> CORRUPTLY_TOXIC_FLAME_LIGHT_LEGGINGS = ITEMS.register("corruptly_toxic_flame_light_leggings",
            () -> new ArmorItem(ModArmorMaterials.MEDICARE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final DeferredItem<ArmorItem> CORRUPTLY_TOXIC_FLAME_LIGHT_BOOTS = ITEMS.register("corruptly_toxic_flame_light_boots",
            () -> new ArmorItem(ModArmorMaterials.MEDICARE, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final DeferredItem<Item> CORRUPTLY_TOXIC_FLAME_HEART = ITEMS.register("corruptly_toxic_flame_heart",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CORRUPTLY_TOXIC_FLAME_PUNCH_0 = ITEMS.register("corruptly_toxic_flame_punch_0",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CORRUPTLY_TOXIC_FLAME_PUNCH_1 = ITEMS.register("corruptly_toxic_flame_punch_1",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

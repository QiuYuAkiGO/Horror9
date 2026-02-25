package net.qiuyu.horror9.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.register.ModItems;

public class ModItemModelGen extends ItemModelProvider {
    public static final String GENERATED = "item/generated";
//    public static final String HANDHELD = "item/handheld";
    public static final String EGG_TEMPLATE = "item/template_spawn_egg";

    public ModItemModelGen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Horror9.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        eggItem(ModItems.NO1_SPAWN_EGG.get());
        eggItem(ModItems.BITER_SPAWN_EGG.get());
        eggItem(ModItems.THE_MISTAKEN_SPAWN_EGG.get());

        withExistingParent(itemName(ModItems.NULL_TRIDENT.get()), modLoc("item/null_trident_in_hand"));
        withExistingParent("null_trident_2d", GENERATED).texture("layer0", resourceItem("null_trident_texture"));

        itemGeneratedModel(ModItems.HEART_METAL.get(), resourceItem("heart_metal_texture"));
        itemGeneratedModel(ModItems.PROTECT_STONE.get(), resourceItem("protect_stone"));
        itemGeneratedModel(ModItems.MEDICARE_HELMET.get(), resourceItem("medicare_helmet_texture"));
        itemGeneratedModel(ModItems.MEDICARE_CHESTPLATE.get(), resourceItem("medicare_chestplate_texture"));
        itemGeneratedModel(ModItems.MEDICARE_LEGGINGS.get(), resourceItem("medicare_leggings_texture"));
        itemGeneratedModel(ModItems.MEDICARE_BOOTS.get(), resourceItem("medicare_boots_texture"));

        handheldItem(ModItems.OLD_DIAMOND_SWORD.get());

        // Corruption basic materials - use their own migrated textures
        itemGeneratedModel(ModItems.CORRUPTED_BRAIN.get(), resourceItem("corrupted_brain"));
        itemGeneratedModel(ModItems.CORRUPTED_CHITIN.get(), resourceItem("corrupted_chitin"));
        itemGeneratedModel(ModItems.CORRUPTED_STICK.get(), resourceItem("corrupted_stick"));
        itemGeneratedModel(ModItems.CORRUPTED_FOREGUT.get(), resourceItem("corrupted_Foregut".toLowerCase()));
        itemGeneratedModel(ModItems.DARK_SOUL.get(), resourceItem("dark_Soul".toLowerCase()));
        itemGeneratedModel(ModItems.COTTON.get(), resourceItem("cotton"));
        itemGeneratedModel(ModItems.VEIN.get(), resourceItem("vein"));
        itemGeneratedModel(ModItems.TOXIC_FLAME_CRYSTALLIZATION.get(), resourceItem("toxic_flame_crystallization"));
        itemGeneratedModel(ModItems.WATER_LAVA_CRYSTALLIZATION.get(), resourceItem("water_lava_crystallization"));
        itemGeneratedModel(ModItems.NITROCELLULOSE.get(), resourceItem("nitrocellulose"));
        itemGeneratedModel(ModItems.TOOTH.get(), resourceItem("tooth"));
        itemGeneratedModel(ModItems.BOTTLE_OF_TOXIC_FLAME.get(), resourceItem("bottle_of_toxic_flame"));
        itemGeneratedModel(ModItems.CORRECTLY_SNOW_BALL.get(), resourceItem("correctly_snow_ball"));

        // Corruption weapons & tools - use their own icons
        handheldItem(ModItems.CORRUPTLY_LAVA_SNOW_BROAD_SWORD.get());
        handheldItem(ModItems.CORRUPTLY_LAVA_SNOW_KNIFE.get());
        handheldItem(ModItems.CORRUPTLY_TOXIC_FLAME_SWORD.get());
        handheldItem(ModItems.SAND_WATER_THROWING_KNIFE.get());

        // Bow uses vanilla-style bow model; texture name comes from item id
        withExistingParent(itemName(ModItems.CORRUPTLY_TOXIC_FLAME_BOW.get()), "item/bow");

        // Corruption armor - use their own item textures
        itemGeneratedModel(ModItems.CORRUPTLY_LAVA_SNOW_HEAVY_HELMET.get(), resourceItem("corruptly_lava_snow_heavy_helmet"));
        itemGeneratedModel(ModItems.CORRUPTLY_LAVA_SNOW_HEAVY_CHESTPLATE.get(), resourceItem("corruptly_lava_snow_heavy_chestplate"));
        itemGeneratedModel(ModItems.CORRUPTLY_LAVA_SNOW_HEAVY_LEGGINGS.get(), resourceItem("corruptly_lava_snow_heavy_leggings"));
        itemGeneratedModel(ModItems.CORRUPTLY_LAVA_SNOW_HEAVY_BOOTS.get(), resourceItem("corruptly_lava_snow_heavy_boots"));

        itemGeneratedModel(ModItems.CORRUPTLY_SAND_WATER_LIGHT_HELMET.get(), resourceItem("corruptly_sand_water_light_helmet"));
        itemGeneratedModel(ModItems.CORRUPTLY_SAND_WATER_LIGHT_CHESTPLATE.get(), resourceItem("corruptly_sand_water_light_chestplate"));
        itemGeneratedModel(ModItems.CORRUPTLY_SAND_WATER_LIGHT_LEGGINGS.get(), resourceItem("corruptly_sand_water_light_leggings"));
        itemGeneratedModel(ModItems.CORRUPTLY_SAND_WATER_LIGHT_BOOTS.get(), resourceItem("corruptly_sand_water_light_boots"));

        itemGeneratedModel(ModItems.CORRUPTLY_TOXIC_FLAME_HEAVY_HELMET.get(), resourceItem("corruptly_toxic_flame_heavy_helmet"));
        itemGeneratedModel(ModItems.CORRUPTLY_TOXIC_FLAME_HEAVY_CHESTPLATE.get(), resourceItem("corruptly_toxic_flame_heavy_chestplate"));
        itemGeneratedModel(ModItems.CORRUPTLY_TOXIC_FLAME_HEAVY_LEGGINGS.get(), resourceItem("corruptly_toxic_flame_heavy_leggings"));
        itemGeneratedModel(ModItems.CORRUPTLY_TOXIC_FLAME_HEAVY_BOOTS.get(), resourceItem("corruptly_toxic_flame_heavy_boots"));

        itemGeneratedModel(ModItems.CORRUPTLY_TOXIC_FLAME_LIGHT_HELMET.get(), resourceItem("corruptly_toxic_flame_light_helmet"));
        itemGeneratedModel(ModItems.CORRUPTLY_TOXIC_FLAME_LIGHT_CHESTPLATE.get(), resourceItem("corruptly_toxic_flame_light_chestplate"));
        itemGeneratedModel(ModItems.CORRUPTLY_TOXIC_FLAME_LIGHT_LEGGINGS.get(), resourceItem("corruptly_toxic_flame_light_leggings"));
        itemGeneratedModel(ModItems.CORRUPTLY_TOXIC_FLAME_LIGHT_BOOTS.get(), resourceItem("corruptly_toxic_flame_light_boots"));

        // Heart & Punch - use their own migrated textures
        itemGeneratedModel(ModItems.CORRUPTLY_TOXIC_FLAME_HEART.get(), resourceItem("corruptly_toxic_flame_heart"));
        itemGeneratedModel(ModItems.CORRUPTLY_TOXIC_FLAME_PUNCH_0.get(), resourceItem("corruptly_toxic_flame_punch_0"));
        itemGeneratedModel(ModItems.CORRUPTLY_TOXIC_FLAME_PUNCH_1.get(), resourceItem("corruptly_toxic_flame_punch_1"));
    }

    private void eggItem(Item item) {
        withExistingParent(itemName(item),
                EGG_TEMPLATE);
    }

    public void itemGeneratedModel(Item item, ResourceLocation texture) {
        withExistingParent(itemName(item), GENERATED).texture("layer0", texture);
    }

    private String itemName(Item item) {
        if (item == null) return "";
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }

    public ResourceLocation resourceBlock(String path) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "block/" + path);
    }

    public ResourceLocation resourceItem(String path) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "item/" + path);
    }
}

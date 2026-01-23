package net.qiuyu.horror9.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
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
        itemGeneratedModel(ModItems.MEDICARE_HELMET.get(), resourceItem("medicare_helmet_texture"));
        itemGeneratedModel(ModItems.MEDICARE_CHESTPLATE.get(), resourceItem("medicare_chestplate_texture"));
        itemGeneratedModel(ModItems.MEDICARE_LEGGINGS.get(), resourceItem("medicare_leggings_texture"));
        itemGeneratedModel(ModItems.MEDICARE_BOOTS.get(), resourceItem("medicare_boots_texture"));
        itemGeneratedModel(ModItems.CREATOR_PHONE.get(), resourceItem("creator_phone_texture"));
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
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }

    public ResourceLocation resourceBlock(String path) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "block/" + path);
    }

    public ResourceLocation resourceItem(String path) {
        return ResourceLocation.parse(Horror9.MODID + ":" + "item/" + path);
    }
}

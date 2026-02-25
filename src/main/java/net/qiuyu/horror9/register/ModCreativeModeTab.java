package net.qiuyu.horror9.register;


import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.qiuyu.horror9.Horror9;

public class ModCreativeModeTab {

    public static final String HORROR9_TAB_STRING = "creativetab.horror9_tab";
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Horror9.MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> HORROR9_TAB = CREATIVE_MODE_TABS.register("horror9_tab",
            ()-> CreativeModeTab.builder().icon(()->new ItemStack(ModItems.NO1_SPAWN_EGG.get()))
                    .title(Component.translatable(HORROR9_TAB_STRING))
                    .displayItems((pParameters, pOutput) -> {
                        // 物品
//                        pOutput.accept(ModItems.NULL_TRIDENT.get());
                        pOutput.accept(ModItems.NO1_SPAWN_EGG.get());
                        pOutput.accept(ModItems.BITER_SPAWN_EGG.get());
                        pOutput.accept(ModItems.THE_MISTAKEN_SPAWN_EGG.get());
                        pOutput.accept(ModItems.VILLAGE_CORE_RED_SPAWN_EGG.get());
                        pOutput.accept(ModItems.VILLAGE_CORE_BLUE_SPAWN_EGG.get());
                        pOutput.accept(ModItems.OWL_SICKLE.get());
                        pOutput.accept(ModItems.HEART_METAL.get());
                        pOutput.accept(ModItems.HEART_PASS.get());
                        pOutput.accept(ModItems.OXYGEN_DESTROYER.get());
                        pOutput.accept(ModItems.MEDICARE_HELMET.get());
                        pOutput.accept(ModItems.MEDICARE_CHESTPLATE.get());
                        pOutput.accept(ModItems.MEDICARE_LEGGINGS.get());
                        pOutput.accept(ModItems.MEDICARE_BOOTS.get());
                        pOutput.accept(ModItems.WITHER_BOMB.get());
                        pOutput.accept(ModItems.CREATOR_PHONE.get());
                        pOutput.accept(ModItems.PROTECT_STONE.get());

                        // Corruption basic materials
                        pOutput.accept(ModItems.CORRUPTED_BRAIN.get());
                        pOutput.accept(ModItems.CORRUPTED_CHITIN.get());
                        pOutput.accept(ModItems.CORRUPTED_STICK.get());
                        pOutput.accept(ModItems.CORRUPTED_FOREGUT.get());
                        pOutput.accept(ModItems.DARK_SOUL.get());
                        pOutput.accept(ModItems.COTTON.get());
                        pOutput.accept(ModItems.VEIN.get());
                        pOutput.accept(ModItems.TOXIC_FLAME_CRYSTALLIZATION.get());
                        pOutput.accept(ModItems.WATER_LAVA_CRYSTALLIZATION.get());
                        pOutput.accept(ModItems.NITROCELLULOSE.get());
                        pOutput.accept(ModItems.TOOTH.get());
                        pOutput.accept(ModItems.BOTTLE_OF_TOXIC_FLAME.get());
                        pOutput.accept(ModItems.CORRECTLY_SNOW_BALL.get());

                        // Corruption weapons & tools
                        pOutput.accept(ModItems.CORRUPTLY_LAVA_SNOW_BROAD_SWORD.get());
                        pOutput.accept(ModItems.CORRUPTLY_LAVA_SNOW_KNIFE.get());
                        pOutput.accept(ModItems.CORRUPTLY_TOXIC_FLAME_SWORD.get());
                        pOutput.accept(ModItems.SAND_WATER_THROWING_KNIFE.get());
                        pOutput.accept(ModItems.CORRUPTLY_TOXIC_FLAME_BOW.get());

                        // Corruption armor
                        pOutput.accept(ModItems.CORRUPTLY_LAVA_SNOW_HEAVY_HELMET.get());
                        pOutput.accept(ModItems.CORRUPTLY_LAVA_SNOW_HEAVY_CHESTPLATE.get());
                        pOutput.accept(ModItems.CORRUPTLY_LAVA_SNOW_HEAVY_LEGGINGS.get());
                        pOutput.accept(ModItems.CORRUPTLY_LAVA_SNOW_HEAVY_BOOTS.get());

                        pOutput.accept(ModItems.CORRUPTLY_SAND_WATER_LIGHT_HELMET.get());
                        pOutput.accept(ModItems.CORRUPTLY_SAND_WATER_LIGHT_CHESTPLATE.get());
                        pOutput.accept(ModItems.CORRUPTLY_SAND_WATER_LIGHT_LEGGINGS.get());
                        pOutput.accept(ModItems.CORRUPTLY_SAND_WATER_LIGHT_BOOTS.get());

                        pOutput.accept(ModItems.CORRUPTLY_TOXIC_FLAME_HEAVY_HELMET.get());
                        pOutput.accept(ModItems.CORRUPTLY_TOXIC_FLAME_HEAVY_CHESTPLATE.get());
                        pOutput.accept(ModItems.CORRUPTLY_TOXIC_FLAME_HEAVY_LEGGINGS.get());
                        pOutput.accept(ModItems.CORRUPTLY_TOXIC_FLAME_HEAVY_BOOTS.get());

                        pOutput.accept(ModItems.CORRUPTLY_TOXIC_FLAME_LIGHT_HELMET.get());
                        pOutput.accept(ModItems.CORRUPTLY_TOXIC_FLAME_LIGHT_CHESTPLATE.get());
                        pOutput.accept(ModItems.CORRUPTLY_TOXIC_FLAME_LIGHT_LEGGINGS.get());
                        pOutput.accept(ModItems.CORRUPTLY_TOXIC_FLAME_LIGHT_BOOTS.get());

                        pOutput.accept(ModItems.CORRUPTLY_TOXIC_FLAME_HEART.get());
                        pOutput.accept(ModItems.CORRUPTLY_TOXIC_FLAME_PUNCH_0.get());
                        pOutput.accept(ModItems.CORRUPTLY_TOXIC_FLAME_PUNCH_1.get());
                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}

package net.qiuyu.horror9.entity.item;


import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.qiuyu.horror9.Horror9;

public class ModCreativeModeTab {

    public static final String HORROR9_TAB_STRING = "creativetab.horror9_tab";
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Horror9.MODID);
    public static final RegistryObject<CreativeModeTab> HORROR9_TAB = CREATIVE_MODE_TABS.register("horror9_tab",
            ()-> CreativeModeTab.builder().icon(()->new ItemStack(ModItems.NO1_SPAWN_EGG.get()))
                    .title(Component.translatable(HORROR9_TAB_STRING))
                    .displayItems((pParameters, pOutput) -> {
                        // 物品
                        pOutput.accept(ModItems.NO1_SPAWN_EGG.get());
                        pOutput.accept(ModItems.BITER_SPAWN_EGG.get());
                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}

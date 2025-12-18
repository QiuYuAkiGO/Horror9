package net.qiuyu.horror9.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.block.ModBlocks;
import net.qiuyu.horror9.entity.item.ModCreativeModeTab;
import net.qiuyu.horror9.entity.item.ModItems;

public class ModLangGen extends LanguageProvider {
    public ModLangGen(PackOutput output, String locale) {
        super(output, Horror9.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add(ModItems.NO1_SPAWN_EGG.get(),"No1 Egg");
        add(ModCreativeModeTab.HORROR9_TAB_STRING,"Horror9 Tab");
        add("entity.horror9.no1","The No.1");

        // Corruption Wood Blocks
        add(ModBlocks.CORRUPTION_LOG.get(), "Corruption Log");
        add(ModBlocks.CORRUPTION_WOOD.get(), "Corruption Wood");
        add(ModBlocks.CORRUPTION_STRIPPED_LOG.get(), "Stripped Corruption Log");
        add(ModBlocks.CORRUPTION_STRIPPED_WOOD.get(), "Stripped Corruption Wood");
        add(ModBlocks.CORRUPTION_PLANKS.get(), "Corruption Planks");
        add(ModBlocks.CORRUPTION_LEAVES.get(), "Corruption Leaves");
    }
}

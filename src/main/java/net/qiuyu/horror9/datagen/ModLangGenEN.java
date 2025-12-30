package net.qiuyu.horror9.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.register.ModCreativeModeTab;
import net.qiuyu.horror9.register.ModItems;

public class ModLangGenEN extends LanguageProvider {
    public ModLangGenEN(PackOutput output, String locale) {
        super(output, Horror9.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add(ModItems.OWL_SICKLE.get(),"Owl Sickle");
        add(ModItems.NO1_SPAWN_EGG.get(),"No1 Egg");
        add(ModItems.BITER_SPAWN_EGG.get(),"Biter Egg");
        add(ModItems.THE_MISTAKEN_SPAWN_EGG.get(),"The Mistaken Egg");
        add(ModCreativeModeTab.HORROR9_TAB_STRING,"Horror9 Tab");
        add("entity.horror9.no1","The No.1");
        add("entity.horror9.biter","The Biter");
        add("entity.horror9.the_mistaken","The Mistaken");
    }
}

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
        add(ModItems.OWL_SICKLE.get(),"Reaper's Scythe");
        add("tooltip.horror9.owl_sickle.line1", "He finally realized that he had become the blade of 'Misunderstanding'.");
        add("tooltip.horror9.owl_sickle.line2", "\"Heads fall, fate is sealed.\"");
        add(ModItems.NO1_SPAWN_EGG.get(),"No1 Egg");
        add(ModItems.BITER_SPAWN_EGG.get(),"Biter Egg");
        add(ModItems.THE_MISTAKEN_SPAWN_EGG.get(),"The Mistaken Egg");
        add(ModCreativeModeTab.HORROR9_TAB_STRING,"Horror9 Tab");
        add("entity.horror9.no1","The No.1");
        add("entity.horror9.biter","The Biter");
        add("entity.horror9.the_mistaken","The Mistaken");
        
        // Keybindings
        add("key.category.horror9", "Horror9");
        add("key.horror9.shift_left_click", "Shift + Left Click");
        add("key.horror9.shift_right_click", "Shift + Right Click");
        add("key.horror9.shift_middle_click", "Shift + Middle Click");
    }
}

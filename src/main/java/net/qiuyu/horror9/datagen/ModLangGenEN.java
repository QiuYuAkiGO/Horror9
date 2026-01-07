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

        add(ModItems.MEDICARE_HELMET.get(),"Medical Care Department『Hat』");
        add(ModItems.MEDICARE_CHESTPLATE.get(),"Medical Care Department『Coat』");
        add(ModItems.MEDICARE_LEGGINGS.get(),"Medical Care Department『Leggings』");
        add(ModItems.MEDICARE_BOOTS.get(),"Medical Care Department『Shoes』");
        add("tooltip.horror9.medicare.line1", "Medical Care Department member, can excellently take care of everything in the facility,");
        add("tooltip.horror9.medicare.line2", "but the most important is still good at taking care of patients' emotions");

        add(ModItems.OWL_SICKLE.get(),"Reaper's Scythe");
        add("tooltip.horror9.owl_sickle.line1", "He finally realized that he had become the blade of 'Misunderstanding'.");
        add("tooltip.horror9.owl_sickle.line2", "\"Heads fall, fate is sealed.\"");
        add(ModItems.HEART_PASS.get(),"Heart Pass");
        add("tooltip.horror9.heart_pass.line1", "Please remain quiet while the patient is receiving treatment.❤");
        add("tooltip.horror9.heart_pass.line2", "Thank you for your understanding and support.❤");
        add(ModItems.NULL_TRIDENT.get(),"WIPE OUT");
        add("tooltip.horror9.null_trident.line1", "Efficient so called.");
        add("tooltip.horror9.null_trident.line2", "Not funny.");

        add(ModItems.HEART_METAL.get(),"❤『Heart Metal』❤");
        add("tooltip.horror9.heart_metal.line1", "Heart Blood Donation Reward❤");

        add(ModItems.NO1_SPAWN_EGG.get(),"No1 Egg");
        add(ModItems.BITER_SPAWN_EGG.get(),"Biter Egg");
        add(ModItems.THE_MISTAKEN_SPAWN_EGG.get(),"The Mistaken Egg");
        add(ModCreativeModeTab.HORROR9_TAB_STRING,"Horror9");
        add("entity.horror9.no1","The No.1");
        add("entity.horror9.biter","The Biter");
        add("entity.horror9.the_mistaken","The Mistaken");
        
        // Keybindings
        add("key.category.horror9", "Horror9");
        add("key.horror9.ctrl_left_click", "Ctrl + Left Click");
        add("key.horror9.ctrl_right_click", "Ctrl + Right Click");
        add("key.horror9.ctrl_middle_click", "Ctrl + Middle Click");

        add("curios.identifier.chest", "Chest");
    }
}

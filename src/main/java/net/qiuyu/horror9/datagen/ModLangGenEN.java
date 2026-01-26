package net.qiuyu.horror9.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
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
        add(ModItems.OXYGEN_DESTROYER.get(),"Oxygen Destroyer");
        add("tooltip.horror9.oxygen_destroyer.line1", "Based on the skin of a giant beast, it is a gene weapon with its own power system, which can explode when it hits the ground.");
        add("tooltip.horror9.oxygen_destroyer.line2", "Right-click and hold for 2s, the ground can explode, and enemies in front of the fan-shaped range can be knocked back.");
        add(ModItems.WITHER_BOMB.get(), "Wither Bomb");
        add("tooltip.horror9.wither_bomb.line1", "Warning: Explosive device!");
        add("tooltip.horror9.wither_bomb.line2", "Hold right-click to throw. Explodes on impact with a wither gas effect.");
        add(ModItems.OLD_DIAMOND_SWORD.get(), "Diamond Sword");
        add("tooltip.horror9.old_sword.line1", "A sword that has lost its luster but remains sharp");
        add("tooltip.horror9.old_sword.line2", "Attack Damage");
        add("tooltip.horror9.when_in_main_hand", "When in Main Hand:");

        add(ModItems.HEART_METAL.get(),"❤『Heart Metal』❤");
        add("tooltip.horror9.heart_metal.line1", "Heart Blood Donation Reward❤");
        add("tooltip.horror9.heart_metal.line2", "When the wearer is attacked, there is a 30% chance");
        add("tooltip.horror9.heart_metal.line3", "to grant itself Regeneration I, lasting 20s");
        add(ModItems.CREATOR_PHONE.get(), "Creator Phone");
        add(ModItems.YUUKA_HALO.get(), "Yuuka's Halo");

        add("tooltip.horror9.yuuka_halo.line1", "It's logical because the explosion is caused by gravity.");
        add("tooltip.horror9.yuuka_halo.line2", "Grants Speed III when equipped. Causes explosion on fall (Damage 1:1 with distance).");
        add("tooltip.horror9.creator_phone.energy", "Energy: %d%%");
        add("gui.horror9.creator_phone.title", "Select Player to Teleport");

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
        add("curios.identifier.halo", "Halo");
    }
}

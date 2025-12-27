package net.qiuyu.horror9.datagen;


import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.entity.item.ModCreativeModeTab;
import net.qiuyu.horror9.entity.item.ModItems;


public class ModLangGenCN extends LanguageProvider {
    public ModLangGenCN(PackOutput output, String locale) {
        super(output, Horror9.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add(ModItems.NO1_SPAWN_EGG.get(),"一号机原形");
        add(ModItems.BITER_SPAWN_EGG.get(),"活体蔓生者卵");
        add(ModCreativeModeTab.HORROR9_TAB_STRING,"恐怖九号创造栏");
        add("entity.horror9.no1","一号机");
        add("entity.horror9.biter","活体蔓生者");

    }
}

package net.qiuyu.horror9.datagen;


import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.register.ModCreativeModeTab;
import net.qiuyu.horror9.register.ModItems;


public class ModLangGenCN extends LanguageProvider {
    public ModLangGenCN(PackOutput output, String locale) {
        super(output, Horror9.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add(ModItems.OWL_SICKLE.get(),"『枭首之镰』");
        add("tooltip.horror9.owl_sickle.line1", "他终于看清，自己已然成为了“误解”的刀刃。");
        add("tooltip.horror9.owl_sickle.line2", "\"人头落地，既成定局\"");
        add("tooltip.horror9.heart_pass.line1", "患者在接受治疗时请保持安静❤️");
        add("tooltip.horror9.heart_pass.line2", "感谢您的理解与支持❤️");
        add(ModItems.NO1_SPAWN_EGG.get(),"一号机原形");
        add(ModItems.BITER_SPAWN_EGG.get(),"活体蔓生者卵");
        add(ModItems.THE_MISTAKEN_SPAWN_EGG.get(),"误解之人投影");
        add(ModCreativeModeTab.HORROR9_TAB_STRING,"恐怖九号创造栏");
        add("entity.horror9.no1","一号机");
        add("entity.horror9.biter","活体蔓生者");
        add("entity.horror9.the_mistaken","误解之人");

        // 按键翻译
        add("key.category.horror9", "恐怖九号");
        add("key.horror9.shift_left_click", "Shift + 左键");
        add("key.horror9.shift_right_click", "Shift + 右键");
        add("key.horror9.shift_middle_click", "Shift + 中键");
    }
}

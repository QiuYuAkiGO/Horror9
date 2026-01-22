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

        // 护甲
        add(ModItems.MEDICARE_HELMET.get(),"爱心医疗部『帽子』");
        add(ModItems.MEDICARE_CHESTPLATE.get(),"爱心医疗部『大衣』");
        add(ModItems.MEDICARE_LEGGINGS.get(),"爱心医疗部『裤子』");
        add(ModItems.MEDICARE_BOOTS.get(),"爱心医疗部『防尘鞋』");
        add("tooltip.horror9.medicare.line1", "爱心医疗部的部员，能够出色的兼顾到设施的一切，");
        add("tooltip.horror9.medicare.line2", "但最主要的还是擅长照顾病人的情绪");

        // 武器
        add(ModItems.OWL_SICKLE.get(),"『枭首之镰』");
        add("tooltip.horror9.owl_sickle.line1", "他终于看清，自己已然成为了“误解”的刀刃。");
        add("tooltip.horror9.owl_sickle.line2", "\"人头落地，既成定局\"");
        add(ModItems.HEART_PASS.get(),"『传递爱心』");
        add("tooltip.horror9.heart_pass.line1", "患者在接受治疗时请保持安静❤");
        add("tooltip.horror9.heart_pass.line2", "感谢您的理解与支持❤");
        add(ModItems.NULL_TRIDENT.get(),"『存在抹除』");
        add("tooltip.horror9.null_trident.line1", "我们称之为高效。");
        add("tooltip.horror9.null_trident.line2", "不嘻嘻。");
        add(ModItems.OXYGEN_DESTROYER.get(),"『撼地者』");
        add("tooltip.horror9.oxygen_destroyer.line1", "基于某个巨兽表皮而放置的基因武器,自带动力系统,可以在锤击地面时产生爆炸.");
        add("tooltip.horror9.oxygen_destroyer.line2", "右键长按2s,可以是地面发生爆炸,击飞面前扇形范围的敌人.");
        add(ModItems.WITHER_BOMB.get(), "凋零炸弹");

        // 饰品
        add(ModItems.HEART_METAL.get(),"❤『爱心勋章』❤");
        add("tooltip.horror9.heart_metal.line1", "已发放爱心献血奖励❤");
        add("tooltip.horror9.heart_metal.line2", "当佩戴者受到攻击时,有百分之30%的概率");
        add("tooltip.horror9.heart_metal.line3", "为自身赋予生命回复1的效果,持续20s");

        add(ModItems.CREATOR_PHONE.get(), "什庭之匣");
        add("tooltip.horror9.creator_phone.energy", "能量: %d%%");
        add("gui.horror9.creator_phone.title", "选择玩家进行传送");

        // 实体
        add(ModItems.NO1_SPAWN_EGG.get(),"一号机原形");
        add(ModItems.BITER_SPAWN_EGG.get(),"活体蔓生者卵");
        add(ModItems.THE_MISTAKEN_SPAWN_EGG.get(),"误解之人投影");
        add(ModCreativeModeTab.HORROR9_TAB_STRING,"恐怖九号");
        add("entity.horror9.no1","一号机");
        add("entity.horror9.biter","活体蔓生者");
        add("entity.horror9.the_mistaken","误解之人");

        // 按键翻译
        add("key.category.horror9", "恐怖九号");
        add("key.horror9.ctrl_left_click", "Ctrl + 左键");
        add("key.horror9.ctrl_right_click", "Ctrl + 右键");
        add("key.horror9.ctrl_middle_click", "Ctrl + 中键");

        add("curios.identifier.chest", "胸部");
    }
}

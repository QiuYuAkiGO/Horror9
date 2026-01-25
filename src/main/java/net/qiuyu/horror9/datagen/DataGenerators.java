package net.qiuyu.horror9.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.qiuyu.horror9.Horror9;

@EventBusSubscriber(modid = Horror9.MODID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        ExistingFileHelper helper = event.getExistingFileHelper();
        generator.addProvider(event.includeClient(),new ModItemModelGen(output, helper));
        generator.addProvider(event.includeClient(),new ModLangGenEN(output,"en_us"));
        generator.addProvider(event.includeClient(),new ModLangGenCN(output,"zh_cn"));
        generator.addProvider(event.includeClient(),new ModBlockModelGen(output,helper));

    }
}

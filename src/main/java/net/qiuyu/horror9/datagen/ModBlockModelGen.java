package net.qiuyu.horror9.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.qiuyu.horror9.Horror9;


public class ModBlockModelGen extends BlockStateProvider {

    public ModBlockModelGen(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Horror9.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
    }
    public ResourceLocation blockTexture(Block block) {
        ResourceLocation name = key(block);
        return ResourceLocation.parse(name.getNamespace() + ":" + ModelProvider.BLOCK_FOLDER + "/" + name.getPath());
    }
    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    private String name(Block block) {
        return key(block).getPath();
    }
}

package net.qiuyu.horror9.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.block.ModBlocks;


public class ModBlockModelGen extends BlockStateProvider {

    public ModBlockModelGen(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Horror9.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // Corruption Wood Set
        axisBlock((RotatedPillarBlock) ModBlocks.CORRUPTION_LOG.get(), blockTexture(ModBlocks.CORRUPTION_LOG.get()));
        axisBlock((RotatedPillarBlock) ModBlocks.CORRUPTION_WOOD.get(), blockTexture(ModBlocks.CORRUPTION_WOOD.get()));
        axisBlock((RotatedPillarBlock) ModBlocks.CORRUPTION_STRIPPED_LOG.get(), blockTexture(ModBlocks.CORRUPTION_STRIPPED_LOG.get()));
        axisBlock((RotatedPillarBlock) ModBlocks.CORRUPTION_STRIPPED_WOOD.get(), blockTexture(ModBlocks.CORRUPTION_STRIPPED_WOOD.get()));
        simpleBlock(ModBlocks.CORRUPTION_PLANKS.get());
        simpleBlock(ModBlocks.CORRUPTION_LEAVES.get(), models().cubeAll(name(ModBlocks.CORRUPTION_LEAVES.get()), blockTexture(ModBlocks.CORRUPTION_LEAVES.get())).renderType("cutout"));
    }
    public ResourceLocation blockTexture(Block block) {
        ResourceLocation name = key(block);
        return new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath());
    }
    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    private String name(Block block) {
        return key(block).getPath();
    }
}

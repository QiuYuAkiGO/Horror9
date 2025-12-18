package net.qiuyu.horror9.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGen extends BlockTagsProvider {
    public ModBlockTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Horror9.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        // Corruption Wood Tags
        tag(BlockTags.MINEABLE_WITH_AXE).add(
                ModBlocks.CORRUPTION_LOG.get(),
                ModBlocks.CORRUPTION_WOOD.get(),
                ModBlocks.CORRUPTION_STRIPPED_LOG.get(),
                ModBlocks.CORRUPTION_STRIPPED_WOOD.get(),
                ModBlocks.CORRUPTION_PLANKS.get()
        );

        tag(BlockTags.LOGS).add(
                ModBlocks.CORRUPTION_LOG.get(),
                ModBlocks.CORRUPTION_WOOD.get(),
                ModBlocks.CORRUPTION_STRIPPED_LOG.get(),
                ModBlocks.CORRUPTION_STRIPPED_WOOD.get()
        );

        tag(BlockTags.PLANKS).add(ModBlocks.CORRUPTION_PLANKS.get());
        tag(BlockTags.LEAVES).add(ModBlocks.CORRUPTION_LEAVES.get());
        tag(BlockTags.OVERWORLD_NATURAL_LOGS).add(ModBlocks.CORRUPTION_LOG.get());
    }
}

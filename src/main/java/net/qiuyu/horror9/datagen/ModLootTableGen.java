package net.qiuyu.horror9.datagen;

import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.block.ModBlocks;

import java.util.Map;
import java.util.stream.Collectors;


public class ModLootTableGen extends VanillaBlockLoot  {

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.CORRUPTION_LOG.get());
        this.dropSelf(ModBlocks.CORRUPTION_WOOD.get());
        this.dropSelf(ModBlocks.CORRUPTION_STRIPPED_LOG.get());
        this.dropSelf(ModBlocks.CORRUPTION_STRIPPED_WOOD.get());
        this.dropSelf(ModBlocks.CORRUPTION_PLANKS.get());
        this.add(ModBlocks.CORRUPTION_LEAVES.get(), block ->
                createLeavesDrops(block, ModBlocks.CORRUPTION_LEAVES.get(), NORMAL_LEAVES_SAPLING_CHANCES));
    }
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getEntries().stream()
                .filter(e -> e.getKey().location().getNamespace().equals(Horror9.MODID))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

}

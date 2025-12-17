package net.qiuyu.horror9.datagen;

import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.qiuyu.horror9.Horror9;

import java.util.Map;
import java.util.stream.Collectors;


public class ModLootTableGen extends VanillaBlockLoot  {

    @Override
    protected void generate() {

    }
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getEntries().stream()
                .filter(e -> e.getKey().location().getNamespace().equals(Horror9.MODID))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

}

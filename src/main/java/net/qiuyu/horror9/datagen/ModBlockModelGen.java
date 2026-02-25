package net.qiuyu.horror9.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.register.ModBlocks;

public class ModBlockModelGen extends BlockStateProvider {

    public ModBlockModelGen(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Horror9.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // 暂时不为新腐化方块生成 BlockState/Model，避免在方块尚未完全注册/贴图缺失时阻塞 datagen。
        // 如需正式方块模型，可在纹理与注册稳定后再补充。
    }

    public ResourceLocation blockTexture(Block block) {
        ResourceLocation name = key(block);
        return ResourceLocation.parse(name.getNamespace() + ":" + ModelProvider.BLOCK_FOLDER + "/" + name.getPath());
    }

    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    private String name(Block block) {
        return key(block).getPath();
    }
}

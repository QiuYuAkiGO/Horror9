package net.qiuyu.horror9.register;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.qiuyu.horror9.Horror9;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Horror9.MODID);

    // 收集所有通过 registerLeaves 注册的叶子块，便于在客户端一次性注册颜色处理器
    public static final List<DeferredBlock<? extends Block>> LEAVES = new ArrayList<>();

    static <T extends Block> DeferredBlock<T> registerLeaves(String name, Supplier<? extends T> supplier) {
        DeferredBlock<T> ro = BLOCKS.register(name, supplier);
        LEAVES.add(ro);
        return ro;
    }


    static BlockBehaviour.Properties woodProps() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.WOOD)
                .strength(2.0F, 3.0F)
                .sound(SoundType.WOOD);
    }

    private static BlockBehaviour.Properties leavesProps() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.PLANT)
                .strength(0.2F)
                .sound(SoundType.GRASS)
                .noOcclusion()
                .randomTicks();
    }

    private static BlockBehaviour.Properties plantProps() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.PLANT)
                .noCollission()
                .randomTicks()
                .instabreak()
                .sound(SoundType.GRASS);
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    private static <T extends Block> DeferredItem<Item> registerBlockItem(String name, DeferredBlock<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    // Logs
    public static final DeferredBlock<RotatedPillarBlock> SAPINDUS_LOG =
            BLOCKS.register("sapindus_log", () -> new RotatedPillarBlock(woodProps()));

    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_SAPINDUS_LOG =
            BLOCKS.register("stripped_sapindus_log", () -> new RotatedPillarBlock(woodProps()));

    // Wood
    public static final DeferredBlock<RotatedPillarBlock> SAPINDUS_WOOD =
            BLOCKS.register("sapindus_wood", () -> new RotatedPillarBlock(woodProps()));

    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_SAPINDUS_WOOD =
            BLOCKS.register("stripped_sapindus_wood", () -> new RotatedPillarBlock(woodProps()));

    // Planks
    public static final DeferredBlock<Block> SAPINDUS_PLANKS =
            BLOCKS.register("sapindus_planks", () -> new Block(woodProps()));

    // Slab、Stairs
    public static final DeferredBlock<SlabBlock> SAPINDUS_SLAB =
            BLOCKS.register("sapindus_slab", () -> new SlabBlock(woodProps()));

    public static final DeferredBlock<StairBlock> SAPINDUS_STAIRS =
            BLOCKS.register("sapindus_stairs", () -> new StairBlock(SAPINDUS_PLANKS.get().defaultBlockState(), woodProps()));

    // Fence + Fence Gate
    public static final DeferredBlock<FenceBlock> SAPINDUS_FENCE =
            BLOCKS.register("sapindus_fence", () -> new FenceBlock(woodProps()));

    public static final DeferredBlock<FenceGateBlock> SAPINDUS_FENCE_GATE =
            BLOCKS.register("sapindus_fence_gate", () -> new FenceGateBlock(WoodType.OAK, woodProps()));

    // Door + Trapdoor
    public static final DeferredBlock<DoorBlock> SAPINDUS_DOOR =
            BLOCKS.register("sapindus_door", () -> new DoorBlock(BlockSetType.OAK, woodProps().noOcclusion()));

    public static final DeferredBlock<TrapDoorBlock> SAPINDUS_TRAPDOOR =
            BLOCKS.register("sapindus_trapdoor", () -> new TrapDoorBlock(BlockSetType.OAK, woodProps().noOcclusion()));

    // Pressure Plate + Button
    public static final DeferredBlock<PressurePlateBlock> SAPINDUS_PRESSURE_PLATE =
            BLOCKS.register("sapindus_pressure_plate", () -> new PressurePlateBlock(BlockSetType.OAK, woodProps()));

    public static final DeferredBlock<ButtonBlock> SAPINDUS_BUTTON =
            BLOCKS.register("sapindus_button", () -> new ButtonBlock(BlockSetType.OAK, 30, woodProps().noCollission()));

//    private static final AbstractTreeGrower SAPINDUS_TREE_GROWER = new SapindusTreeGrower();
//    // Leaves + Sapling
//    public static final DeferredBlock<SapindusSaplingBlock.SapindusLeavesBlock> SAPINDUS_LEAVES =
//            registerLeaves("sapindus_leaves", () -> new SapindusSaplingBlock.SapindusLeavesBlock(leavesProps()));
//
//    public static final DeferredBlock<SaplingBlock> SAPINDUS_SAPLING =
//            BLOCKS.register("sapindus_sapling", () -> new SaplingBlock(SAPINDUS_TREE_GROWER, plantProps()));
//    //树枝
//    public static final DeferredBlock<Block> SAPINDUS_BRANCH =
//            BLOCKS.register("sapindus_branch", () -> new SapindusSaplingBlock.SapindusBranchBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));
//
//    public static final DeferredBlock<Block> APPLE_BRANCH =
//            BLOCKS.register("apple_branch",
//                    () -> new AppleBranchBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));

}

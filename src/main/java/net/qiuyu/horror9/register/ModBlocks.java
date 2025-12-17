package net.qiuyu.horror9.register;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.qiuyu.horror9.Horror9;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;


public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Horror9.MODID);

    // 收集所有通过 registerLeaves 注册的叶子块，便于在客户端一次性注册颜色处理器
    public static final List<RegistryObject<? extends Block>> LEAVES = new ArrayList<>();

    static <T extends Block> RegistryObject<T> registerLeaves(String name, Supplier<? extends T> supplier) {
        RegistryObject<T> ro = BLOCKS.register(name, supplier);
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

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    // Logs
    public static final RegistryObject<RotatedPillarBlock> SAPINDUS_LOG =
            BLOCKS.register("sapindus_log", () -> new RotatedPillarBlock(woodProps()));

    public static final RegistryObject<RotatedPillarBlock> STRIPPED_SAPINDUS_LOG =
            BLOCKS.register("stripped_sapindus_log", () -> new RotatedPillarBlock(woodProps()));

    // Wood
    public static final RegistryObject<RotatedPillarBlock> SAPINDUS_WOOD =
            BLOCKS.register("sapindus_wood", () -> new RotatedPillarBlock(woodProps()));

    public static final RegistryObject<RotatedPillarBlock> STRIPPED_SAPINDUS_WOOD =
            BLOCKS.register("stripped_sapindus_wood", () -> new RotatedPillarBlock(woodProps()));

    // Planks
    public static final RegistryObject<Block> SAPINDUS_PLANKS =
            BLOCKS.register("sapindus_planks", () -> new Block(woodProps()));

    // Slab、Stairs
    public static final RegistryObject<SlabBlock> SAPINDUS_SLAB =
            BLOCKS.register("sapindus_slab", () -> new SlabBlock(woodProps()));

    private static BlockState defaultStateOfPlanks() {
        return ModBlocks.SAPINDUS_PLANKS.get().defaultBlockState();
    }

    public static final RegistryObject<StairBlock> SAPINDUS_STAIRS =
            BLOCKS.register("sapindus_stairs", () -> new StairBlock(defaultStateOfPlanks(), woodProps()));

    // Fence + Fence Gate
    public static final RegistryObject<FenceBlock> SAPINDUS_FENCE =
            BLOCKS.register("sapindus_fence", () -> new FenceBlock(woodProps()));

    public static final RegistryObject<FenceGateBlock> SAPINDUS_FENCE_GATE =
            BLOCKS.register("sapindus_fence_gate", () -> new FenceGateBlock(woodProps(), WoodType.OAK));

    // Door + Trapdoor
    public static final RegistryObject<DoorBlock> SAPINDUS_DOOR =
            BLOCKS.register("sapindus_door", () -> new DoorBlock(woodProps().noOcclusion(), BlockSetType.OAK));

    public static final RegistryObject<TrapDoorBlock> SAPINDUS_TRAPDOOR =
            BLOCKS.register("sapindus_trapdoor", () -> new TrapDoorBlock(woodProps().noOcclusion(), BlockSetType.OAK));

    // Pressure Plate + Button
    public static final RegistryObject<PressurePlateBlock> SAPINDUS_PRESSURE_PLATE =
            BLOCKS.register("sapindus_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, woodProps(), BlockSetType.OAK));

    public static final RegistryObject<ButtonBlock> SAPINDUS_BUTTON =
            BLOCKS.register("sapindus_button", () -> new ButtonBlock(woodProps().noCollission(), BlockSetType.OAK, 30, true));

//    private static final AbstractTreeGrower SAPINDUS_TREE_GROWER = new SapindusTreeGrower();
//    // Leaves + Sapling
//    public static final RegistryObject<SapindusSaplingBlock.SapindusLeavesBlock> SAPINDUS_LEAVES =
//            registerLeaves("sapindus_leaves", () -> new SapindusSaplingBlock.SapindusLeavesBlock(leavesProps()));
//
//    public static final RegistryObject<SaplingBlock> SAPINDUS_SAPLING =
//            BLOCKS.register("sapindus_sapling", () -> new SaplingBlock(SAPINDUS_TREE_GROWER, plantProps()));
//    //树枝
//    public static final RegistryObject<Block> SAPINDUS_BRANCH =
//            BLOCKS.register("sapindus_branch", () -> new SapindusSaplingBlock.SapindusBranchBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));
//
//    public static final RegistryObject<Block> APPLE_BRANCH =
//            BLOCKS.register("apple_branch",
//                    () -> new AppleBranchBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));

}

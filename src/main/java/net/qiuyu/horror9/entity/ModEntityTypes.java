package net.qiuyu.horror9.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.qiuyu.horror9.Horror9;
import net.qiuyu.horror9.entity.custom.BiterEntity;
import net.qiuyu.horror9.entity.custom.No1Entity;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Horror9.MODID);

    public static final RegistryObject<EntityType<No1Entity>> NO1 =
            ENTITY_TYPES.register("no1",
                    () -> EntityType.Builder.of(No1Entity::new, MobCategory.MONSTER)
                            .sized(3.5f, 4.5f)
                            .build(ResourceLocation.parse(Horror9.MODID + ":" + "no1").toString()));
    public static final RegistryObject<EntityType<BiterEntity>> BITER =
            ENTITY_TYPES.register("biter",
                    () -> EntityType.Builder.of(BiterEntity::new, MobCategory.MONSTER)
                            .sized(0.6f, 0.6f)
                            .build(ResourceLocation.parse(Horror9.MODID + ":" + "biter").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
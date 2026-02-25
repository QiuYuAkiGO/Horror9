package net.qiuyu.horror9.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.qiuyu.horror9.Horror9;

public class ModDamageTypes {
    public static final ResourceKey<DamageType> GIANT_KILLER_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE,
            ResourceLocation.fromNamespaceAndPath(Horror9.MODID, "giant_killer_damage"));
}

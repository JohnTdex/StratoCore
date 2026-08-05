package net.johntdex.stratocore.block.entity;

import net.johntdex.stratocore.StratoCore;
import net.johntdex.stratocore.block.StratoBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class StratoBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, StratoCore.MODID);

    public static final Supplier<BlockEntityType<StratoCorpBeaconBlockEntity>> STRATOCORP_BEACON_BE = BLOCK_ENTITIES.register("stratocorp_beacon_be",
            () -> BlockEntityType.Builder.of(StratoCorpBeaconBlockEntity::new, StratoBlocks.STRATOCORP_BEACON.get()).build(null));

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}

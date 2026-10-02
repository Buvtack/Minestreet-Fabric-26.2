package com.buvtack.minestreet.blocks;

import com.buvtack.minestreet.WolfOfMinestreet;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class ModBlockEntities {

    public static BlockEntityType<TradingStationBlockEntity> TRADING_STATION;

    public static void register() {
        TRADING_STATION = Registry.register(
                BuiltInRegistries.BLOCK_ENTITY_TYPE,
                Identifier.fromNamespaceAndPath(WolfOfMinestreet.MODID, "trading_station"),
                FabricBlockEntityTypeBuilder.create(
                        TradingStationBlockEntity::new,
                        ModBlocks.TRADING_STATION
                ).build()
        );
    }
}

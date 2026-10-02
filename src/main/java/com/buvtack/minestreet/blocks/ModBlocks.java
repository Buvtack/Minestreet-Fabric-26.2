package com.buvtack.minestreet.blocks;

import com.buvtack.minestreet.WolfOfMinestreet;
import com.buvtack.minestreet.items.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class ModBlocks {

    public static final TradingStationBlock TRADING_STATION = (TradingStationBlock) register("trading_station", TradingStationBlock::new, BlockBehaviour.Properties.of().strength(3.5F));

    private static Block register(String name, Function<BlockBehaviour.Properties, ? extends Block> function, BlockBehaviour.Properties properties) {
        Block block = function.apply(properties.setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(WolfOfMinestreet.MODID, name))));
        registerBlockItem(name, block);
        return Registry.register(
                BuiltInRegistries.BLOCK,
                Identifier.fromNamespaceAndPath(WolfOfMinestreet.MODID, name),
                block
        );
    }

    private static void registerBlockItem(String name, Block block) {
        BlockItem blockItem = new BlockItem(block, new Item.Properties().useBlockDescriptionPrefix()
                .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(WolfOfMinestreet.MODID, name))));

        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(WolfOfMinestreet.MODID, name), blockItem);
        ModItems.TRADING_STATION = blockItem;
    }
}

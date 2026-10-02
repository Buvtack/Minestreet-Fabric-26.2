package com.buvtack.minestreet.items;

import com.buvtack.minestreet.WolfOfMinestreet;
import com.buvtack.minestreet.blocks.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public class ModItems {

    public static BlockItem TRADING_STATION;

    public static void init() {
         TRADING_STATION = new BlockItem(
                 ModBlocks.TRADING_STATION,
                 new Item.Properties().useBlockDescriptionPrefix()
                         .setId(
                                 ResourceKey.create(
                                     Registries.ITEM,
                                     Identifier.fromNamespaceAndPath(WolfOfMinestreet.MODID, "trading_station")
                                 )
                         )
         );
    }
}

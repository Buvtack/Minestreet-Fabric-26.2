package com.buvtack.minestreet.gui;

import com.buvtack.minestreet.WolfOfMinestreet;
import com.buvtack.minestreet.blocks.ModBlocks;
import com.buvtack.minestreet.items.ModItems;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTabs {

    public static final ResourceKey<CreativeModeTab> MOD_TAB = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB,
            Identifier.fromNamespaceAndPath(WolfOfMinestreet.MODID, "mod_tab")
    );

    public static final CreativeModeTab WOLF_TAB = FabricCreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.TRADING_STATION))
            .title(Component.literal("Wolf of Minestreet"))
            .displayItems((displayParameters, output) -> {
                output.accept(ModItems.TRADING_STATION);
            })
            .build();
}

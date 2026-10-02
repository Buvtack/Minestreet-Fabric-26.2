package com.buvtack.minestreet.gui;

import com.buvtack.minestreet.WolfOfMinestreet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class ModMenus {

    public static MenuType<TradingStationMenu> TRADING_STATION;

    public static void register() {
        TRADING_STATION = Registry.register(
                BuiltInRegistries.MENU,
                Identifier.fromNamespaceAndPath(WolfOfMinestreet.MODID, "trading_station"),
                new MenuType<>(TradingStationMenu::new, FeatureFlags.DEFAULT_FLAGS)
        );
    }
}

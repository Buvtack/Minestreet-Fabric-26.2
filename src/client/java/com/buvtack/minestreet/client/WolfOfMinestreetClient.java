package com.buvtack.minestreet.client;

import com.buvtack.minestreet.StockMarket;
import com.buvtack.minestreet.client.gui.screens.TradingStationScreen;
import com.buvtack.minestreet.client.network.Packets;
import com.buvtack.minestreet.gui.ModMenus;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;

import java.util.concurrent.CompletableFuture;

public class WolfOfMinestreetClient implements ClientModInitializer {

	private static long time = System.nanoTime();

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		Packets.registerClient();
		ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
			Positions.positions.clear();
		});

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (System.nanoTime() - time >= StockMarket.CLEAN_INTERVAL && !Minecraft.getInstance().isPaused()) {
				time = System.nanoTime();
				CompletableFuture.runAsync(StockMarketClient::clean);
			}
		});

		MenuScreens.register(ModMenus.TRADING_STATION, TradingStationScreen::new);
	}
}
package com.buvtack.minestreet;

import com.buvtack.minestreet.blocks.ModBlockEntities;
import com.buvtack.minestreet.blocks.ModBlocks;
import com.buvtack.minestreet.blocks.TradingStationBlock;
import com.buvtack.minestreet.common.Position;
import com.buvtack.minestreet.gui.ModCreativeTabs;
import com.buvtack.minestreet.gui.ModMenus;
import com.buvtack.minestreet.items.ModItems;
import com.buvtack.minestreet.network.Packets;
import com.buvtack.minestreet.network.packets.InitStockPacket;
import com.buvtack.minestreet.network.packets.OrderResponsePacket;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class WolfOfMinestreet implements ModInitializer {

	public static final String MODID = "minestreet";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	private static MinecraftServer server;
	private static long time = System.nanoTime();

	@Override
	public void onInitialize() {
		Packets.register();
		registerItemsAndBlocks();
		ModBlockEntities.register();
		ModMenus.register();

		ServerLifecycleEvents.SERVER_STARTING.register(currentServer -> server = currentServer);
		ServerLifecycleEvents.SERVER_STOPPING.register(currentServer -> server = null);

		ServerLifecycleEvents.SERVER_STARTING.register(minecraftServer -> StockMarket.initialize());

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			ServerPlayer player = handler.getPlayer();
			sendInitialPacketsToPlayer(player);
			sendPositionsOfPlayer(player);
		});

		ServerTickEvents.END_SERVER_TICK.register(minecraftServer -> {
			if (System.nanoTime() - time >= StockMarket.CLEAN_INTERVAL) {
				time = System.nanoTime();
				CompletableFuture.runAsync(StockMarket::clean);
			}
		});
	}

	private void sendInitialPacketsToPlayer(ServerPlayer player) {
		JsonObject result = new JsonObject();
		JsonArray array = new JsonArray();
		for (String ticker : StockMarket.INITIAL_TICKERS) {
			JsonObject stock = StockMarket.get(ticker);
			array.add(stock);
		}

		for (JsonElement element : StockMarket.positionsOf(player)) {
			JsonObject object = element.getAsJsonObject();
			String ticker = object.get(StockMarketKeys.TICKER).getAsString();
			JsonObject stock = StockMarket.get(ticker);
			if (!array.contains(stock))
				array.add(stock);
		}

		result.add("array", array);
		//PacketDistributor.sendToPlayer(player, new InitStockPacket(result.toString()));
		ServerPlayNetworking.send(player, new InitStockPacket(result.toString()));
	}

	private void sendPositionsOfPlayer(ServerPlayer player) {
		Path positionFile = StockMarket.getOrInitPathFile(player.getUUID());
		JsonObject root = Position.getFileAsJsonObject(positionFile);
		JsonArray positions = root.get(StockMarketKeys.POSITIONS).getAsJsonArray();
		positions.forEach(element -> {
			JsonObject position = element.getAsJsonObject();
			String positionStr = position.toString();
			//PacketDistributor.sendToPlayer(player, new OrderResponsePacket(true, positionStr));
			ServerPlayNetworking.send(player, new OrderResponsePacket(true, positionStr));
		});
	}

	private void registerItemsAndBlocks() {
		Registry.register(
				BuiltInRegistries.CREATIVE_MODE_TAB,
				ModCreativeTabs.MOD_TAB,
				ModCreativeTabs.WOLF_TAB
		);
	}

	public static MinecraftServer server() {
		return server;
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MODID, path);
	}
}

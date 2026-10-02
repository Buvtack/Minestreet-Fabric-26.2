package com.buvtack.minestreet.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.buvtack.minestreet.StockMarket;
import com.buvtack.minestreet.StockMarketKeys;
import com.buvtack.minestreet.WolfOfMinestreet;
import com.buvtack.minestreet.client.gui.components.StockEntry;
import com.buvtack.minestreet.client.gui.screens.TradingStationScreen;
import com.buvtack.minestreet.common.Position;
import com.buvtack.minestreet.network.packets.GetStockPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StockMarketClient {

    public static JsonObject get(String ticker) {
        ticker = ticker.toUpperCase().trim();
        if (!StockMarket.storedStocks.containsKey(ticker)) {
            ClientPlayNetworking.send(new GetStockPacket(ticker));
        }
        return StockMarket.storedStocks.get(ticker);
    }

    public static void clean() {
        WolfOfMinestreet.LOGGER.info("CLEANING");
        Set<String> keptTickers = new HashSet<>();

        for (String ticker : StockMarket.INITIAL_TICKERS) {
            get(ticker);
            keptTickers.add(ticker);
        }

        if (WolfOfMinestreet.server() != null)
            for (ServerPlayer player : WolfOfMinestreet.server().getPlayerList().getPlayers()) {
                JsonObject root = Position.getFileAsJsonObject(StockMarket.getOrInitPathFile(player.getUUID()));
                JsonArray array = root.get(StockMarketKeys.POSITIONS).getAsJsonArray();
                for (JsonElement element : array) {
                    String ticker = element.getAsJsonObject().get(StockMarketKeys.TICKER).getAsString();
                    get(ticker);
                    keptTickers.add(ticker);
                }
            }

        if (ModHelper.screen() instanceof TradingStationScreen screen) {
            List<StockEntry> searchedStocks = new ArrayList<>(screen.getSearchedStocks());
            for (StockEntry entry : searchedStocks) {
                String ticker = entry.stock.get(StockMarketKeys.TICKER).getAsString();
                get(ticker);
                keptTickers.add(ticker);
            }

            screen.refresh();
        }

        StockMarket.storedStocks.keySet().removeIf(ticker -> !keptTickers.contains(ticker));
    }
}

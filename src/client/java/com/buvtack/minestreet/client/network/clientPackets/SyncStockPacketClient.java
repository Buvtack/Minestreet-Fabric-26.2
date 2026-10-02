package com.buvtack.minestreet.client.network.clientPackets;

import com.buvtack.minestreet.StockMarket;
import com.buvtack.minestreet.StockMarketKeys;
import com.buvtack.minestreet.client.gui.screens.TradingStationScreen;
import com.buvtack.minestreet.network.packets.SyncStockPacket;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

import java.util.concurrent.CompletableFuture;

public class SyncStockPacketClient {

    public static void handle(SyncStockPacket packet, ClientPlayNetworking.Context context) {
        CompletableFuture.runAsync(() -> {
            JsonObject jsonObject = JsonParser.parseString(packet.stocks()).getAsJsonObject();
            JsonArray array = jsonObject.get("array").getAsJsonArray();
            array.forEach(element -> {
                String ticker = element.getAsJsonObject().get(StockMarketKeys.TICKER).getAsString();
                StockMarket.storedStocks.put(ticker, element.getAsJsonObject());
            });

            Screen screen = Minecraft.getInstance().gui.screen();
            if (screen instanceof TradingStationScreen tsScreen) {
                Minecraft.getInstance().execute(tsScreen::updateStockEntryList);
            }
        });
    }
}

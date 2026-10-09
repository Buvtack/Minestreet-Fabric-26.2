package com.buvtack.minestreet.client.network.clientPackets;

import com.buvtack.minestreet.StockMarket;
import com.buvtack.minestreet.StockMarketKeys;
import com.buvtack.minestreet.client.Logos;
import com.buvtack.minestreet.client.gui.screens.TradingStationScreen;
import com.buvtack.minestreet.network.packets.InitStockPacket;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public class InitStockPacketClient {

    public static void handle(InitStockPacket packet, ClientPlayNetworking.Context context) {
        Minecraft.getInstance().execute(() -> {
            JsonObject json = JsonParser.parseString(packet.stocks()).getAsJsonObject();
            JsonArray array = json.getAsJsonArray("array");
            array.forEach(element -> {
                JsonObject stock = element.getAsJsonObject();
                String ticker = stock.get(StockMarketKeys.TICKER).getAsString();
                //shit
                StockMarket.storedStocks.put(ticker, stock);
                Logos.addLogo(stock);
            });

            Screen screen = Minecraft.getInstance().gui.screen();
            if (screen instanceof TradingStationScreen tsScreen)
                tsScreen.updateStockEntryList();
        });
    }
}

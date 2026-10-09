package com.buvtack.minestreet.client.network.clientPackets;

import com.buvtack.minestreet.StockMarket;
import com.buvtack.minestreet.StockMarketKeys;
import com.buvtack.minestreet.client.Logos;
import com.buvtack.minestreet.network.packets.GetStockResponsePacket;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;

public class GetStockResponsePacketClient {

    public static void handle(GetStockResponsePacket packet, ClientPlayNetworking.Context context) {
        Minecraft.getInstance().execute(() -> {
            JsonObject stock = JsonParser.parseString(packet.stockJson()).getAsJsonObject();
            StockMarket.storedStocks.put(stock.get(StockMarketKeys.TICKER).getAsString(), stock);
            Logos.addLogo(stock);
        });
    }
}

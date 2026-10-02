package com.buvtack.minestreet.network.packets;

import com.buvtack.minestreet.StockMarket;
import com.buvtack.minestreet.WolfOfMinestreet;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import java.util.concurrent.CompletableFuture;

public record SearchStockPacket(String searched) implements CustomPacketPayload {

    public static final Type<SearchStockPacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(WolfOfMinestreet.MODID, "request_stock"));


    public static final StreamCodec<FriendlyByteBuf, SearchStockPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, SearchStockPacket::searched,
            SearchStockPacket::new
    );

    public static void handle(SearchStockPacket packet, ServerPlayNetworking.Context context) {
        CompletableFuture.runAsync(() -> {
            StockMarket.searchAndStore(packet.searched);

            JsonObject result = new JsonObject();
            JsonArray array = new JsonArray();
            for (String ticker : StockMarket.storedStocks.keySet()) {
                if (ticker.contains(packet.searched)) {
                    array.add(StockMarket.storedStocks.get(ticker));
                }
            }
            result.add("array", array);

            WolfOfMinestreet.server().execute(() -> {
                ServerPlayNetworking.send(context.player(), new SyncStockPacket(result.toString()));
            });
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

package com.buvtack.minestreet.network.packets;

import com.google.gson.JsonObject;
import com.buvtack.minestreet.StockMarket;
import com.buvtack.minestreet.WolfOfMinestreet;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;

import java.util.concurrent.CompletableFuture;

public record GetStockPacket(String ticker) implements CustomPacketPayload {

    public static final Type<GetStockPacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(WolfOfMinestreet.MODID, "get_stock"));

    public static final StreamCodec<FriendlyByteBuf, GetStockPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, GetStockPacket::ticker,
            GetStockPacket::new
    );

    public static void handle(GetStockPacket packet, ServerPlayNetworking.Context context) {
        CompletableFuture.runAsync(() -> {
            JsonObject stock = StockMarket.get(packet.ticker);
            String stockJson = stock.toString();
            //context.reply(new GetStockResponsePacket(stockJson));
            ServerPlayer sender = context.player();
            ServerPlayNetworking.send(sender, new GetStockResponsePacket(stockJson));
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

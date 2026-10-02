package com.buvtack.minestreet.network.packets;

import com.buvtack.minestreet.StockMarket;
import com.buvtack.minestreet.WolfOfMinestreet;
import com.buvtack.minestreet.common.Order;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SendOrderPacket(String order) implements CustomPacketPayload {

    public static final Type<SendOrderPacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(WolfOfMinestreet.MODID, "send_order"));


    public static final StreamCodec<FriendlyByteBuf, SendOrderPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, SendOrderPacket::order,
            SendOrderPacket::new
    );

    public static void handle(SendOrderPacket packet, ServerPlayNetworking.Context context) {
        WolfOfMinestreet.server().execute(() -> {
            Order order = Order.parse(packet.order);
            WolfOfMinestreet.LOGGER.info("Received send order packet on Server: ");
            WolfOfMinestreet.LOGGER.info("Ticker: " + order.ticker + ", " + "Item: " + order.item.getPath());
            StockMarket.execute(order);
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

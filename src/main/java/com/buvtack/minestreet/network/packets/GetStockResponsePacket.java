package com.buvtack.minestreet.network.packets;

import com.buvtack.minestreet.WolfOfMinestreet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record GetStockResponsePacket(String stockJson) implements CustomPacketPayload {

    public static final Type<GetStockResponsePacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(WolfOfMinestreet.MODID, "get_stock_response"));

    public static final StreamCodec<FriendlyByteBuf, GetStockResponsePacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, GetStockResponsePacket::stockJson,
            GetStockResponsePacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

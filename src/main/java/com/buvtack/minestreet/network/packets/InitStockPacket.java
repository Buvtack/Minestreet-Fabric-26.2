package com.buvtack.minestreet.network.packets;

import com.buvtack.minestreet.WolfOfMinestreet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record InitStockPacket(String stocks) implements CustomPacketPayload {

    public static final Type<InitStockPacket> TYPE = new Type<>(
            Identifier.fromNamespaceAndPath(WolfOfMinestreet.MODID, "init_stock")
    );

    public static final StreamCodec<FriendlyByteBuf, InitStockPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.stringUtf8(500_000), InitStockPacket::stocks,
            InitStockPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

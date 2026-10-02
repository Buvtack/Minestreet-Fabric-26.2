package com.buvtack.minestreet.network.packets;

import com.buvtack.minestreet.WolfOfMinestreet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SyncStockPacket(String stocks) implements CustomPacketPayload {

    public static final Type<SyncStockPacket> TYPE = new Type<>(
            Identifier.fromNamespaceAndPath(WolfOfMinestreet.MODID, "sync_stock")
    );

    public static final StreamCodec<FriendlyByteBuf, SyncStockPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, SyncStockPacket::stocks,
            SyncStockPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

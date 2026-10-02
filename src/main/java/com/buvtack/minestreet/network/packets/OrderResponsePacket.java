package com.buvtack.minestreet.network.packets;

import com.buvtack.minestreet.WolfOfMinestreet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record OrderResponsePacket(boolean success, String position) implements CustomPacketPayload {

    public static final Type<OrderResponsePacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(WolfOfMinestreet.MODID, "order_response"));

    public static final StreamCodec<FriendlyByteBuf, OrderResponsePacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, OrderResponsePacket::success,
            ByteBufCodecs.STRING_UTF8, OrderResponsePacket::position,
            OrderResponsePacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

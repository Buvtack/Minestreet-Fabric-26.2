package com.buvtack.minestreet.network;

import com.buvtack.minestreet.network.packets.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class Packets {

    public static void register() {
        PayloadTypeRegistry.serverboundPlay().register(
                GetStockPacket.TYPE,
                GetStockPacket.CODEC
        );

        PayloadTypeRegistry.clientboundPlay().register(
                GetStockPacket.TYPE,
                GetStockPacket.CODEC
        );

        ServerPlayNetworking.registerGlobalReceiver(
                GetStockPacket.TYPE,
                GetStockPacket::handle
        );

        PayloadTypeRegistry.serverboundPlay().register(
                GetStockResponsePacket.TYPE,
                GetStockResponsePacket.CODEC
        );

        PayloadTypeRegistry.clientboundPlay().register(
                GetStockResponsePacket.TYPE,
                GetStockResponsePacket.CODEC
        );

        PayloadTypeRegistry.serverboundPlay().register(
                InitStockPacket.TYPE,
                InitStockPacket.CODEC
        );

        PayloadTypeRegistry.clientboundPlay().register(
                InitStockPacket.TYPE,
                InitStockPacket.CODEC
        );

        PayloadTypeRegistry.serverboundPlay().register(
                OrderResponsePacket.TYPE,
                OrderResponsePacket.CODEC
        );

        PayloadTypeRegistry.clientboundPlay().register(
                OrderResponsePacket.TYPE,
                OrderResponsePacket.CODEC
        );

        PayloadTypeRegistry.serverboundPlay().register(
                SearchStockPacket.TYPE,
                SearchStockPacket.CODEC
        );

        PayloadTypeRegistry.clientboundPlay().register(
                SearchStockPacket.TYPE,
                SearchStockPacket.CODEC
        );

        ServerPlayNetworking.registerGlobalReceiver(
                SearchStockPacket.TYPE,
                SearchStockPacket::handle
        );

        PayloadTypeRegistry.serverboundPlay().register(
                SendOrderPacket.TYPE,
                SendOrderPacket.CODEC
        );

        PayloadTypeRegistry.clientboundPlay().register(
                SendOrderPacket.TYPE,
                SendOrderPacket.CODEC
        );

        ServerPlayNetworking.registerGlobalReceiver(
                SendOrderPacket.TYPE,
                SendOrderPacket::handle
        );

        PayloadTypeRegistry.serverboundPlay().register(
                SyncStockPacket.TYPE,
                SyncStockPacket.CODEC
        );

        PayloadTypeRegistry.clientboundPlay().register(
                SyncStockPacket.TYPE,
                SyncStockPacket.CODEC
        );
    }
}

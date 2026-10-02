package com.buvtack.minestreet.client.network;

import com.buvtack.minestreet.client.network.clientPackets.GetStockResponsePacketClient;
import com.buvtack.minestreet.client.network.clientPackets.InitStockPacketClient;
import com.buvtack.minestreet.client.network.clientPackets.OrderResponsePacketClient;
import com.buvtack.minestreet.client.network.clientPackets.SyncStockPacketClient;
import com.buvtack.minestreet.network.packets.GetStockResponsePacket;
import com.buvtack.minestreet.network.packets.InitStockPacket;
import com.buvtack.minestreet.network.packets.OrderResponsePacket;
import com.buvtack.minestreet.network.packets.SyncStockPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class Packets {

    public static void registerClient() {
        ClientPlayNetworking.registerGlobalReceiver(
            GetStockResponsePacket.TYPE,
            GetStockResponsePacketClient::handle
        );

        ClientPlayNetworking.registerGlobalReceiver(
                OrderResponsePacket.TYPE,
                OrderResponsePacketClient::handle
        );

        ClientPlayNetworking.registerGlobalReceiver(
                InitStockPacket.TYPE,
                InitStockPacketClient::handle
        );

        ClientPlayNetworking.registerGlobalReceiver(
                SyncStockPacket.TYPE,
                SyncStockPacketClient::handle
        );
    }
}

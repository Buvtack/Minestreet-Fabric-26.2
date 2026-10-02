package com.buvtack.minestreet.client.network.clientPackets;

import com.buvtack.minestreet.client.ModHelper;
import com.buvtack.minestreet.client.Positions;
import com.buvtack.minestreet.client.gui.screens.TradingStationScreen;
import com.buvtack.minestreet.common.Position;
import com.buvtack.minestreet.network.packets.OrderResponsePacket;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;

public class OrderResponsePacketClient {

    public static void handle(OrderResponsePacket packet, ClientPlayNetworking.Context context) {
        if (packet.success()) {
            Minecraft.getInstance().execute(() -> {
                JsonObject object = JsonParser.parseString(packet.position()).getAsJsonObject();
                Position position = Position.fromJsonObject(object);
                Positions.positions.put(position.clientId(), position);
                if (ModHelper.screen() instanceof TradingStationScreen screen) {
                    screen.updatePositionEntryList();
                    screen.updateInventory();
                }
            });
        }
    }
}

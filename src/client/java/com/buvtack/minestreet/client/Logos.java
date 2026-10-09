package com.buvtack.minestreet.client;

import com.buvtack.minestreet.StockMarket;
import com.buvtack.minestreet.StockMarketKeys;
import com.buvtack.minestreet.WolfOfMinestreet;
import com.buvtack.minestreet.client.gui.ModColors;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.Identifier;

import java.io.IOException;
import java.util.Base64;

public class Logos {

    public static void addLogo(JsonObject stock) {
        String ticker = StockMarket.ticker(stock);
        if (!stock.has(StockMarketKeys.LOGO)) {
            NativeImage image = new NativeImage(100, 100, true);
            image.fillRect(0, 0, image.getWidth(), image.getHeight(), ModColors.TRANSPARENT.color);
            registerImage(ticker, image);
        }

        byte[] pngBytes = Base64.getDecoder().decode(stock.get(StockMarketKeys.LOGO).getAsString());
        try {
            NativeImage image = NativeImage.read(pngBytes);
            registerImage(ticker, image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void registerImage(String ticker, NativeImage image) {
        ModHelper.roundImage(image);
        String imagePath = "logo/" + ticker.toLowerCase();
        DynamicTexture texture = new DynamicTexture(() -> imagePath, image);
        Identifier textureId = Identifier.fromNamespaceAndPath(WolfOfMinestreet.MODID, imagePath);
        Minecraft.getInstance().getTextureManager().register(textureId, texture);
    }

    public static Identifier logo(String ticker) {
        return Identifier.fromNamespaceAndPath(WolfOfMinestreet.MODID, "logo/" + ticker.toLowerCase());
    }
}

package com.buvtack.minestreet.client;

import com.buvtack.minestreet.WolfOfMinestreet;
import com.buvtack.minestreet.client.gui.ModColor;
import com.buvtack.minestreet.client.gui.ModColors;
import com.buvtack.minestreet.client.gui.screens.TradingStationScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.glfw.GLFW;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class ModHelper {

    private static final NumberFormat compactFormatter = NumberFormat.getCompactNumberInstance(
            Locale.US,
            NumberFormat.Style.SHORT
    );

    private static final NumberFormat simpleFormatter = new DecimalFormat("#,##0.00");

    static {
        compactFormatter.setMaximumFractionDigits(2);
        compactFormatter.setMinimumFractionDigits(0);
    }

    public static boolean isNotLeftClicking() {
        long windowHandle = Minecraft.getInstance().getWindow().handle();
        return GLFW.glfwGetMouseButton(windowHandle, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_RELEASE;
    }

    public static boolean isLeftClicking() {
        long windowHandle = Minecraft.getInstance().getWindow().handle();
        return GLFW.glfwGetMouseButton(windowHandle, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS;
    }

    public static Player player() {
        return Minecraft.getInstance().player;
    }

    public static Screen screen() {
        return Minecraft.getInstance().gui.screen();
    }
    public static TradingStationScreen tradingScreen() {
        return (TradingStationScreen) screen();
    }

    public static boolean isTradingStation() {
        return screen() instanceof TradingStationScreen;
    }

    public static boolean selectedStack() {
        TradingStationScreen screen = (TradingStationScreen) screen();
        return screen.getSelectedStack() != null && !screen.getSelectedStack().isEmpty();
    }

    public static int getItemCount(ItemStack stack) {
        Player player = Minecraft.getInstance().player;
        Inventory inventory = player.getInventory();
        int result = 0;
        for (ItemStack inventoryStack : inventory) {
            if (stack.is(inventoryStack.getItem()))
                result += inventoryStack.count();
        }
        return result;
    }

    public static List<String> split(String text, int width) {
        List<String> words = Arrays.asList(text.split(" "));
        List<String> result = new LinkedList<>();
        String line = "";
        for (String word : words) {
            int wordLength = word.length();
            int lineLength = line.length();
            if (lineLength + wordLength <= width)
                line += word + " ";
            else {
                line = line.substring(0, line.length() - 1);
                result.add(new String(line));
                line = word + " ";
            }
        }
        if (!line.isBlank())
            result.add(line);

        WolfOfMinestreet.LOGGER.info("Result of splitting String: " + Integer.toString(result.size()));

        return result;
    }

    public static String format(double d) {
        if (d >= 10_000)
            return compactFormatter.format(d);

        return simpleFormatter.format(d);
    }

    public static ModColor getColor(Number num) {
        return num.doubleValue() >= 0 ? (num.doubleValue() > 0 ? ModColors.PROFIT : ModColors.WHITE) : ModColors.LOSS;
    }
}

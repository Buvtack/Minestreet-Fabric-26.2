package com.buvtack.minestreet;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.glfw.GLFW;

public class CommonModHelper {

    public static Item item(Identifier id) {
        return BuiltInRegistries.ITEM.getValue(id);
    }

    public static int getItemCount(ServerPlayer player, Identifier itemId) {
        Item item = item(itemId);
        int total = 0;

        for (ItemStack stack : player.getInventory())
            if (!stack.isEmpty() && stack.is(item))
                total += stack.getCount();

        ItemStack offhand = player.getOffhandItem();
        if (!offhand.isEmpty() && offhand.is(item))
            total += offhand.getCount();

        return total;
    }

    public static int getAvailableInventorySpace(ServerPlayer player, Item item) {
        Inventory inventory = player.getInventory();
        int maxStackSize = item.getDefaultMaxStackSize();
        int result = 0;

        for (int i = 0; i < 36; i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.isEmpty())
                result += maxStackSize;
            else if (stack.is(item))
                result += (maxStackSize - stack.getCount());
        }

        return result;
    }

    public static void addItemToPlayer(ServerPlayer player, Item item, int amount) {
        player.getInventory().add(new ItemStack(item, amount));
        player.containerMenu.broadcastChanges();
        player.inventoryMenu.broadcastChanges();
    }
}

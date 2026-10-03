package com.buvtack.minestreet.client.gui.screens;

import com.buvtack.minestreet.client.ModMouseHandler;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

import java.util.HashMap;
import java.util.Map;

public abstract class ModScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {

    protected GuiGraphicsExtractor graphics;
    protected int mouseX;
    protected int mouseY;
    protected float partialTick;

    protected Map<String, Runnable> tickTasks;
    protected Map<String, Runnable> renderTasks;

    public ModScreen(T menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        tickTasks = new HashMap<>();
        renderTasks = new HashMap<>();
    }

    public void addTickTask(String name, Runnable task) {
        tickTasks.put(name, task);
    }

    public Runnable removeTickTask(String name) {
        return tickTasks.remove(name);
    }

    public void addRenderTask(String name, Runnable task) {
        renderTasks.put(name, task);
    }

    public Runnable removeRenderTask(String name) {
        return renderTasks.remove(name);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
        this.graphics = graphics;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.partialTick = partialTick;

        ModMouseHandler.mouseX = mouseX;
        ModMouseHandler.mouseY = mouseY;
    }
}

package com.buvtack.minestreet.client.gui.components;

import com.buvtack.minestreet.client.gui.ModColors;
import net.minecraft.network.chat.Component;

public class DividendPanel extends DimensionalModComponent {

    public static final int WIDTH = 228;
    public static final int HEIGHT = 168;

    private ModLabel comingSoon;

    public DividendPanel(int x, int y) {
        super(x, y, WIDTH, HEIGHT, 3);
        comingSoon = new ModLabel(x + WIDTH / 2, y + HEIGHT / 5, Component.literal("The dividends page is under construction!"), ModColors.WHITE, ModLabel.Alignment.CENTER);
    }

    @Override
    public void doTick() {
        comingSoon.doTick();
    }

    @Override
    public void doRender() {
        comingSoon.render(graphics, mouseX, mouseY, partialTick);
    }
}

package me.abtu.graphics.ui;

import me.abtu.Main;
import me.abtu.graphics.GraphicsBuffer;
import processing.core.PConstants;
import processing.core.PGraphics;

public class PlayerStats extends GraphicsBuffer {
    public PlayerStats(Main main, String renderer) {
        super(main, renderer);
        drawBackground = false;
    }

    @Override
    protected void drawBuffer(Main main, PGraphics graphics, float mouseX, float mouseY) {
        graphics.noStroke();
        graphics.fill(0xAA000000);
        graphics.rectMode(PConstants.CORNER);
        graphics.rect(0, 0, REFERENCE_WIDTH, REFERENCE_HEIGHT / 9f);
    }
}

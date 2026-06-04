package me.abtu.graphics.game;

import me.abtu.Main;
import me.abtu.graphics.GraphicsBuffer;
import processing.core.PConstants;
import processing.core.PGraphics;

public class GameArena extends GraphicsBuffer {

    public GameArena(Main main, int resizeMode) {
        super(main, resizeMode);
    }

    @Override
    protected void drawBuffer(Main main, PGraphics graphics, float mouseX, float mouseY) {
        graphics.rectMode(PConstants.CENTER);
        
        graphics.rect(HALF_WIDTH, HALF_HEIGHT, REFERENCE_WIDTH, REFERENCE_HEIGHT);
    }
}

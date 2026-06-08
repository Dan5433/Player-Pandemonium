package me.abtu.graphics.game;

import me.abtu.Main;
import me.abtu.game.entity.Entity;
import me.abtu.graphics.GraphicsBuffer;
import processing.core.PGraphics;

public class EntityGraphics extends GraphicsBuffer {

    public EntityGraphics(Main main, int resizeMode) {
        super(main, resizeMode);
    }

    @Override
    protected void drawBuffer(Main main, PGraphics graphics, float mouseX, float mouseY) {
        for (Entity entity : main.getEntities())
            entity.draw(graphics);
    }
}

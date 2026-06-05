package me.abtu.game;

import processing.core.PConstants;
import processing.core.PGraphics;

public class Platform {
    protected final float centerX, centerY, width, height;
    protected final int fillColor;

    public Platform(float centerX, float centerY, float width, float height, int fillColor) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.width = width;
        this.height = height;
        this.fillColor = fillColor;
    }

    public boolean canObjectStandOn(float objectStartX, float objectEndX, float objectEndY) {
        return objectEndX >= centerX - width / 2f && objectStartX <= centerX + width / 2f
                && objectEndY >= centerY - height / 2f && objectEndY <= centerY;
    }

    public void draw(PGraphics graphics) {
        graphics.rectMode(PConstants.CENTER);
        graphics.noStroke();
        graphics.fill(fillColor);
        graphics.rect(centerX, centerY, width, height);
    }

    public float getTopSurfaceY() {
        return centerY - height / 2f;
    }
}

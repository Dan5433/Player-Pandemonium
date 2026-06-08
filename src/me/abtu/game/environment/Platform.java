package me.abtu.game.environment;

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

    /**
     * Check if an object can stand on this platform's top surface.
     * Only allows collision with the top surface, not the sides or bottom.
     *
     * @param objectStartX       left edge of object
     * @param objectEndX         right edge of object
     * @param objectEndY         bottom edge of object (current frame)
     * @param objectEndYPrevious bottom edge of object (previous frame)
     * @param velocityY          vertical velocity (positive = downward)
     * @return true if object would collide with the top surface
     */
    public boolean canObjectStandOn(float objectStartX, float objectEndX, float objectEndY, float objectEndYPrevious, float velocityY) {
        float platformTop = getTopSurfaceY();

        //check if object x is within platform
        boolean horizontalOverlap = objectEndX >= centerX - width / 2f && objectStartX <= centerX + width / 2f;

        //check if velocity is downward
        boolean isMovingDownward = velocityY >= 0;

        //check if object was above before applying current frame velocity
        boolean wasPreviouslyAbove = objectEndYPrevious <= platformTop;

        //check if object would be below if current frame velocity is applied
        boolean isNowAtOrBelow = objectEndY >= platformTop;

        return horizontalOverlap && isMovingDownward && wasPreviouslyAbove && isNowAtOrBelow;
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

package me.abtu.game.entity;

import me.abtu.Main;
import processing.core.PGraphics;
import processing.core.PVector;

public abstract class Entity {
    protected final float width;
    protected final float height;

    protected float previousFrameX, previousFrameY;
    protected float x, y;
    protected PVector velocity;

    public Entity(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocity = new PVector(0, 0);
    }

    public void update(Main main) {
        if (!shouldUpdate())
            return;
        
        previousFrameX = x;
        previousFrameY = y;
        updateInternal(main);
    }

    protected boolean shouldUpdate() {
        return true;
    }

    protected abstract void updateInternal(Main main);

    public abstract void draw(PGraphics graphics);

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getPreviousFrameX() {
        return previousFrameX;
    }

    public float getPreviousFrameY() {
        return previousFrameY;
    }
}

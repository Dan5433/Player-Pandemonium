package me.abtu.game.entity;

import processing.core.PVector;

public abstract class Entity {
    protected final float width;
    protected final float height;

    protected float x, y;
    protected PVector velocity;

    public Entity(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocity = new PVector(0, 0);
    }
}

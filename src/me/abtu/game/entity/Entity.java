package me.abtu.game.entity;

import me.abtu.Main;
import processing.core.PGraphics;
import processing.core.PVector;

public abstract class Entity implements Cloneable {
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

    public boolean collidedWith(Entity other) {
        if (other == null)
            return false;

        final float top = y - height / 2f;
        final float bottom = y + height / 2f;
        final float left = x - width / 2f;
        final float right = x + width / 2f;
        final float previousFrameTop = previousFrameY - height / 2f;
        final float previousFrameBottom = previousFrameY + height / 2f;
        final float previousFrameLeft = previousFrameX - width / 2f;
        final float previousFrameRight = previousFrameX + width / 2f;

        final float topOther = other.y - other.height / 2f;
        final float bottomOther = other.y + other.height / 2f;
        final float leftOther = other.x - other.width / 2f;
        final float rightOther = other.x + other.width / 2f;
        final float previousFrameTopOther = other.previousFrameY - other.height / 2f;
        final float previousFrameBottomOther = other.previousFrameY + other.height / 2f;
        final float previousFrameLeftOther = other.previousFrameX - other.width / 2f;
        final float previousFrameRightOther = other.previousFrameX + other.width / 2f;

        //check hit by comparing positions of previous and current frame
        //prevents phasing through on large game steps
        final boolean withinPlayerX = left >= leftOther && right <= rightOther;
        final boolean withinPlayerY = bottom >= topOther && top <= bottomOther;
        final boolean hitTop = bottom >= topOther && previousFrameBottom <= previousFrameTopOther;
        final boolean hitBottom = top <= bottomOther && previousFrameTop >= previousFrameBottomOther;
        final boolean hitLeft = right >= leftOther && previousFrameRight <= previousFrameLeftOther;
        final boolean hitRight = left <= rightOther && previousFrameLeft >= previousFrameRightOther;

        // Check if currently overlapping (AABB collision)
        final boolean currentlyOverlappingX = right >= leftOther && left <= rightOther;
        final boolean currentlyOverlappingY = bottom >= topOther && top <= bottomOther;
        final boolean currentlyOverlapping = currentlyOverlappingX && currentlyOverlappingY;

        return (withinPlayerY || hitTop || hitBottom) && (withinPlayerX || hitLeft || hitRight) || currentlyOverlapping;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    @Override
    public Entity clone() {
        try {
            Entity clone = (Entity) super.clone();
            clone.velocity = new PVector(velocity.x, velocity.y);
            clone.x = x;
            clone.y = y;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

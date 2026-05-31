package me.abtu.graphics;

import processing.core.PConstants;
import processing.core.PGraphics;

public class Button {
    protected final float x, y;
    protected final int drawMode;
    protected final int normalColor = 255;
    protected final int hoverColor = 220;
    protected final int pressedColor = 200;
    protected float width, height;
    protected State state = State.NORMAL;

    public Button(float x, float y, float width, float height, int drawMode) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.drawMode = drawMode;
    }

    public void draw(PGraphics graphics) {
        final int previousFill = graphics.fillColor;

        final int color = switch (state) {
            case NORMAL -> normalColor;
            case HOVERED -> hoverColor;
            case PRESSED -> pressedColor;
        };
        graphics.fill(color);

        graphics.rectMode(drawMode);
        graphics.rect(x, y, width, height);

        graphics.fill(previousFill);
    }

    public void update(float mouseX, float mouseY, boolean mousePressed) {
        if (isMouseInside(mouseX, mouseY)) {
            if (mousePressed)
                state = State.PRESSED;
            else
                state = State.HOVERED;
        } else
            state = State.NORMAL;
    }

    public boolean isMouseInside(float mouseX, float mouseY) {
        switch (drawMode) {
            case PConstants.CORNER -> {
                if (mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height)
                    return true;
            }
            case PConstants.RADIUS -> {
                if (mouseX > x - width && mouseX < x + width && mouseY > y - height && mouseY < y + height)
                    return true;
            }
            case PConstants.CENTER -> {
                if (mouseX > x - width / 2f && mouseX < x + width / 2f && mouseY > y - height / 2f && mouseY < y + height / 2f)
                    return true;
            }
            default -> throw new IllegalStateException("Unexpected draw mode: " + drawMode);
        }
        return false;
    }

    public enum State {
        NORMAL,
        HOVERED,
        PRESSED
    }
}

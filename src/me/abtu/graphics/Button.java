package me.abtu.graphics;

import processing.core.PConstants;
import processing.core.PGraphics;

public class Button {
    protected final float x, y;
    protected final int drawMode;

    protected State state = State.NORMAL;
    protected float width, height;

    protected String text;
    protected int textColor = 0;

    protected int strokeColor = 0;
    protected float strokeWeight = 1f;

    protected int normalColor = 255;
    protected int hoverColor = 220;
    protected int pressedColor = 200;

    protected Runnable callback;

    public Button(float x, float y, float width, float height, int drawMode, Runnable callback, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.drawMode = drawMode;
        this.callback = callback;
        this.text = text;
    }

    public Button(float x, float y, float width, float height, int drawMode, Runnable callback, String text, int textColor) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.drawMode = drawMode;
        this.callback = callback;
        this.text = text;
        this.textColor = textColor;
    }

    public Button(float x, float y, float width, float height, int drawMode, Runnable callback, String text, int textColor, int normalColor, int hoverColor, int pressedColor) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.drawMode = drawMode;
        this.callback = callback;
        this.text = text;
        this.textColor = textColor;
        this.normalColor = normalColor;
        this.hoverColor = hoverColor;
        this.pressedColor = pressedColor;
    }

    public Button(float x, float y, float width, float height, int drawMode, Runnable callback, String text, int textColor, int normalColor, int hoverColor, int pressedColor, int strokeColor, float strokeWeight) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.drawMode = drawMode;
        this.callback = callback;
        this.text = text;
        this.textColor = textColor;
        this.normalColor = normalColor;
        this.hoverColor = hoverColor;
        this.pressedColor = pressedColor;
        this.strokeColor = strokeColor;
        this.strokeWeight = strokeWeight;
    }

    public void draw(PGraphics graphics) {
        final int previousFill = graphics.fillColor;
        final int previousStroke = graphics.strokeColor;
        final float previousStrokeWeight = graphics.strokeWeight;

        final int color = switch (state) {
            case NORMAL -> normalColor;
            case HOVERED -> hoverColor;
            case PRESSED -> pressedColor;
        };
        graphics.fill(color);

        graphics.stroke(strokeColor);
        graphics.strokeWeight(strokeWeight);

        graphics.rectMode(drawMode);
        graphics.rect(x, y, width, height);

        graphics.fill(textColor);
        switch (drawMode) {
            case PConstants.CORNER -> graphics.textAlign(PConstants.LEFT, PConstants.TOP);
            case PConstants.RADIUS, PConstants.CENTER -> graphics.textAlign(PConstants.CENTER, PConstants.CENTER);
        }
        graphics.text(text, x, y);

        graphics.fill(previousFill);
        graphics.stroke(previousStroke);
        graphics.strokeWeight(previousStrokeWeight);
    }

    public void update(float mouseX, float mouseY, boolean mousePressed) {
        if (isMouseInside(mouseX, mouseY)) {
            if (mousePressed)
                state = State.PRESSED;
            else {
                if (state == State.PRESSED)
                    onPress();

                state = State.HOVERED;
            }
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

    protected void onPress() {
        callback.run();
    }

    protected enum State {
        NORMAL,
        HOVERED,
        PRESSED
    }
}

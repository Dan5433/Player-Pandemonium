package me.abtu.graphics.buttons;

import processing.core.PConstants;
import processing.core.PGraphics;

public class Button {
    protected final float x, y, width, height;
    protected final int drawMode;
    protected final Runnable callback;

    protected final String text;
    protected final int textColor;

    protected final int strokeColor;
    protected final float strokeWeight;

    protected final int normalColor;
    protected final int hoverColor;
    protected final int pressedColor;


    protected State state = State.NORMAL;


    private Button(Builder builder) {
        this.x = builder.x;
        this.y = builder.y;
        this.width = builder.width;
        this.height = builder.height;
        this.drawMode = builder.drawMode;
        this.callback = builder.pressCallback;
        this.text = builder.text;
        this.textColor = builder.textColor;
        this.strokeColor = builder.strokeColor;
        this.strokeWeight = builder.strokeWeight;
        this.normalColor = builder.normalColor;
        this.hoverColor = builder.hoverColor;
        this.pressedColor = builder.pressedColor;
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

    public static class Builder {
        private final float x, y, width, height;
        private final int drawMode;
        private final Runnable pressCallback;

        private String text = "";
        private int textColor = 0;

        private int strokeColor = 0;
        private float strokeWeight = 1f;

        private int normalColor = 255;
        private int hoverColor = 220;
        private int pressedColor = 200;


        public Builder(float x, float y, float width, float height, int drawMode, Runnable pressCallback) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.drawMode = drawMode;
            this.pressCallback = pressCallback;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder textColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder buttonColors(int normalColor, int hoverColor, int pressedColor) {
            this.normalColor = normalColor;
            this.hoverColor = hoverColor;
            this.pressedColor = pressedColor;
            return this;
        }

        public Builder buttonStroke(int strokeColor, float strokeWeight) {
            this.strokeColor = strokeColor;
            this.strokeWeight = strokeWeight;
            return this;
        }

        public Button build() {
            return new Button(this);
        }
    }
}

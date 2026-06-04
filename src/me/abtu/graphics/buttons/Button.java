package me.abtu.graphics.buttons;

import processing.core.PConstants;
import processing.core.PGraphics;

import java.util.function.Consumer;

public class Button {
    protected final float x, y, width, height;
    protected final int drawMode;
    protected final Consumer<Button> callback;
    protected final String tag;
    protected final int textColor;

    protected final int strokeColor;
    protected final float strokeWeight;

    protected final int normalColor;
    protected final int hoverColor;
    protected final int pressedColor;
    protected final int disabledColor;

    protected final int hoverExpand;
    protected String text;


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
        this.hoverExpand = builder.hoverExpand;
        this.tag = builder.tag;
        this.disabledColor = builder.disabledColor;
    }


    public void draw(PGraphics graphics) {
        final int previousFill = graphics.fillColor;
        final int previousStroke = graphics.strokeColor;
        final float previousStrokeWeight = graphics.strokeWeight;
        final int previousTextAlignX = graphics.textAlign;
        final int previousTextAlignY = graphics.textAlignY;

        final int color = switch (state) {
            case NORMAL -> normalColor;
            case HOVERED -> hoverColor;
            case PRESSED -> pressedColor;
            case DISABLED -> disabledColor;
        };
        graphics.fill(color);

        graphics.stroke(strokeColor);
        graphics.strokeWeight(strokeWeight);

        graphics.rectMode(drawMode);
        if (state == State.NORMAL || state == State.DISABLED)
            graphics.rect(x, y, width, height);
        else
            graphics.rect(x, y, width + hoverExpand * 2, height + hoverExpand * 2);

        graphics.fill(textColor);
        switch (drawMode) {
            case PConstants.CORNER -> graphics.textAlign(PConstants.LEFT, PConstants.TOP);
            case PConstants.RADIUS, PConstants.CENTER -> graphics.textAlign(PConstants.CENTER, PConstants.CENTER);
        }
        graphics.text(text, x, y);

        graphics.fill(previousFill);
        graphics.stroke(previousStroke);
        graphics.strokeWeight(previousStrokeWeight);
        graphics.textAlign(previousTextAlignX, previousTextAlignY);
    }

    public void update(float mouseX, float mouseY, boolean mousePressed) {
        if (state == State.DISABLED)
            return;

        if (!isMouseInside(mouseX, mouseY)) {
            state = State.NORMAL;
            return;
        }

        if (mousePressed) {
            state = State.PRESSED;
            return;
        }

        if (state == State.PRESSED)
            onPress();
        state = State.HOVERED;
    }

    public void changeText(String text) {
        this.text = text;
    }

    protected boolean isMouseInside(float mouseX, float mouseY) {
        switch (drawMode) {
            case PConstants.CORNER -> {
                if (mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height)
                    return true;
            }
            case PConstants.CENTER -> {
                if (state == State.NORMAL) {
                    if (mouseX > x - width / 2f && mouseX < x + width / 2f && mouseY > y - height / 2f && mouseY < y + height / 2f)
                        return true;
                } else if (mouseX > x - width / 2f - hoverExpand && mouseX < x + width / 2f + hoverExpand
                        && mouseY > y - height / 2f - hoverExpand && mouseY < y + height / 2f + hoverExpand)
                    return true;
            }
            default -> throw new IllegalStateException("Unexpected draw mode: " + drawMode);
        }
        return false;
    }

    protected void onPress() {
        if (callback == null)
            return;

        callback.accept(this);
    }

    public void disable() {
        state = State.DISABLED;
    }

    public String getTag() {
        return tag;
    }

    public void enable() {
        state = State.NORMAL;
    }

    protected enum State {
        NORMAL,
        HOVERED,
        PRESSED,
        DISABLED
    }

    public static class Builder {
        private final float x, y, width, height;
        private final int drawMode;
        private final Consumer<Button> pressCallback;

        private String text = "";
        private int textColor = 0;

        private int strokeColor = 0;
        private float strokeWeight = 1f;

        private int normalColor = 255;
        private int hoverColor = 220;
        private int pressedColor = 200;
        private int disabledColor = 125;

        private int hoverExpand;

        private String tag;


        public Builder(float x, float y, float width, float height, int drawMode, Consumer<Button> pressCallback) {
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

        public Builder buttonColors(int normalColor, int hoverColor, int pressedColor, int disabledColor) {
            this.normalColor = normalColor;
            this.hoverColor = hoverColor;
            this.pressedColor = pressedColor;
            this.disabledColor = disabledColor;
            return this;
        }

        public Builder buttonStroke(int strokeColor, float strokeWeight) {
            this.strokeColor = strokeColor;
            this.strokeWeight = strokeWeight;
            return this;
        }

        public Builder hoverExpand(int hoverExpand) {
            this.hoverExpand = hoverExpand;
            return this;
        }

        public Builder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public Button build() {
            return new Button(this);
        }
    }
}

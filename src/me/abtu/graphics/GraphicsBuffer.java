package me.abtu.graphics;

import me.abtu.Main;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

public abstract class GraphicsBuffer {

    public static final int REFERENCE_WIDTH = 640;
    public static final int REFERENCE_HEIGHT = 360;

    protected int resizeMode;

    public GraphicsBuffer(int resizeMode) {
        this.resizeMode = resizeMode;
    }

    public PImage getGraphicsImage(Main main) {
        PGraphics graphics = main.createGraphics(REFERENCE_WIDTH, REFERENCE_HEIGHT);

        graphics.beginDraw();
        drawBuffer(main, graphics);
        graphics.endDraw();

        PImage image = graphics.get();
        image.resize(main.width, main.height, resizeMode);
        image.filter(PConstants.POINT);
        return image;
    }

    protected abstract void drawBuffer(Main main, PGraphics graphics);
}

package me.abtu.graphics;

import me.abtu.Main;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

public abstract class GraphicsBuffer {

    public static final int REFERENCE_WIDTH = 640;
    public static final int REFERENCE_HEIGHT = 360;

    public static final int HALF_WIDTH = REFERENCE_WIDTH / 2;
    public static final int HALF_HEIGHT = REFERENCE_HEIGHT / 2;
    public static final int QUARTER_HEIGHT = REFERENCE_HEIGHT / 4;

    protected final float scaleToScreenX;
    protected final float scaleToScreenY;
    protected int resizeMode;

    public GraphicsBuffer(PApplet app, int resizeMode) {
        this.resizeMode = resizeMode;
        scaleToScreenX = app.width / (float) REFERENCE_WIDTH;
        scaleToScreenY = app.height / (float) REFERENCE_HEIGHT;

        if (scaleToScreenX % 1 != 0)
            System.out.println("Screen width is not of a common resolution!");

        if (scaleToScreenY % 1 != 0)
            System.out.println("Screen height is not of a common resolution!");
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

package me.abtu.graphics;

import me.abtu.Main;
import processing.core.PConstants;
import processing.core.PGraphics;

public abstract class GraphicsBuffer {

    public static final int REFERENCE_WIDTH = 640;
    public static final int REFERENCE_HEIGHT = 360;

    public static final int HALF_WIDTH = REFERENCE_WIDTH / 2;
    public static final int HALF_HEIGHT = REFERENCE_HEIGHT / 2;
    public static final int FIFTH_HEIGHT = REFERENCE_HEIGHT / 5;

    public static final int TITLE_SIZE = 65;
    public static final int BUTTON_TEXT_SIZE = 32;
    public static final int SMALL_TEXT_SIZE = 16;

    protected final float scaleToScreenX;
    protected final float scaleToScreenY;
    protected final int resizeMode;

    protected int backgroundColor = 255;
    private final PGraphics graphics;
    protected boolean drawBackground = true;


    public GraphicsBuffer(Main main, int resizeMode, String renderer) {
        this.resizeMode = resizeMode;
        scaleToScreenX = main.width / (float) REFERENCE_WIDTH;
        scaleToScreenY = main.height / (float) REFERENCE_HEIGHT;

        if (scaleToScreenX % 1 != 0)
            System.out.println("Screen width is not of a common resolution!");

        if (scaleToScreenY % 1 != 0)
            System.out.println("Screen height is not of a common resolution!");

        graphics = main.createGraphics(REFERENCE_WIDTH, REFERENCE_HEIGHT, renderer);
    }

    public void render(Main main) {
        final float localMouseX = main.mouseX / scaleToScreenX;
        final float localMouseY = main.mouseY / scaleToScreenY;

        graphics.beginDraw();
        graphics.noSmooth();
        if (drawBackground)
            graphics.background(backgroundColor);
        graphics.fill(255);
        graphics.stroke(0);

        drawBuffer(main, graphics, localMouseX, localMouseY);
        graphics.endDraw();

        main.imageMode(PConstants.CORNER);
        main.image(graphics, 0, 0, main.width, main.height);
    }

    protected abstract void drawBuffer(Main main, PGraphics graphics, float mouseX, float mouseY);
}

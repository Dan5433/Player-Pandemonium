package me.abtu;

import me.abtu.graphics.GraphicsBuffer;
import me.abtu.graphics.ui.PlayerMenu;
import me.abtu.graphics.ui.TitleScreen;
import processing.core.PApplet;
import processing.core.PFont;

public final class Main extends PApplet {
    public static void main(String[] args) {
        PApplet.main(Main.class.getName());
    }

    public void settings() {
        fullScreen(P2D);
//        size(GraphicsBuffer.REFERENCE_WIDTH, GraphicsBuffer.REFERENCE_HEIGHT);
        noSmooth();
    }

    // fonts
    private PFont pixelbit, jersey;

    private GraphicsBuffer ui;


    public void setup() {
        loadFonts();

        initializeGraphics();
    }

    private void initializeGraphics() {
        ui = new TitleScreen(this, NEAREST_NEIGHBOR);
    }

    public void draw() {
        background(255);

        image(ui.getGraphicsImage(this), 0, 0);
    }

    private void loadFonts() {
        pixelbit = createFont("fonts/Pixelbit.ttf", 16, false);
        jersey = createFont("fonts/jersey10/Jersey10-Regular.ttf", 20, false);
    }

    public PFont getTitleFont() {
        return jersey;
    }

    public PFont getDefaultFont() {
        return pixelbit;
    }

    public void pressPlayButton() {
        ui = new PlayerMenu(this, NEAREST_NEIGHBOR);
    }
}
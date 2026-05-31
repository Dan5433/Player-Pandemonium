package me.abtu;

import me.abtu.graphics.ui.TitleScreen;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

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
    public PFont pixelbit, jersey;

    private PImage ui;


    public void setup() {
        rectMode(CORNERS);
        loadFonts();

        initializeGraphics();
    }

    private void initializeGraphics() {
        ui = new TitleScreen(NEAREST_NEIGHBOR).getGraphicsImage(this);
    }

    public void draw() {
        background(255);

        image(ui, 0, 0);
    }

    private void loadFonts() {
        pixelbit = createFont("fonts/Pixelbit.ttf", 32, false);
        jersey = createFont("fonts/jersey10/Jersey10-Regular.ttf", 32, false);
    }
}
package me.abtu;

import me.abtu.scenes.Scene;
import me.abtu.scenes.TitleScreen;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.opengl.PGraphicsOpenGL;

public final class Main extends PApplet {
    public static void main(String[] args) {
        PApplet.main(Main.class.getName());
    }

    public void settings() {
        fullScreen(P2D);
    }

    // fonts
    public PFont pixelbit, jersey;

    private PGraphics ui;


    public void setup() {
        ((PGraphicsOpenGL) g).textureSampling(3); //force point texture sampling

        rectMode(CORNERS);
        loadFonts();

        initializeGraphics();
    }

    private void initializeGraphics() {
        ui = new TitleScreen().getGraphics(this);
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
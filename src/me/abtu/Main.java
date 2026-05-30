package me.abtu;

import me.abtu.scenes.Scene;
import me.abtu.scenes.TitleScreen;
import processing.core.PApplet;
import processing.core.PFont;
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

    //scenes
    private Scene[] scenes;


    public void setup() {
        rectMode(CORNERS);
        loadFonts();
        loadScenes();

        ((PGraphicsOpenGL) g).textureSampling(3); //force point texture sampling
    }

    public void draw() {
        background(255);

        image(scenes[0].getGraphics(this), 0, 0);
    }

    private void loadFonts() {
        pixelbit = createFont("fonts/Pixelbit.ttf", 32, false);
        jersey = createFont("fonts/jersey10/Jersey10-Regular.ttf", 32, false);
    }

    private void loadScenes() {
        scenes = new Scene[]{
                new TitleScreen()
        };
    }
}
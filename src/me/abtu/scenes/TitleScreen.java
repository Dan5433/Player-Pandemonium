package me.abtu.scenes;

import me.abtu.Main;
import processing.core.PConstants;
import processing.core.PGraphics;

public class TitleScreen extends Scene {
    @Override
    public PGraphics getGraphics(Main main) {
        PGraphics graphics = main.createGraphics(main.width, main.height);
        graphics.beginDraw();

        graphics.background(255);
        graphics.fill(0);


        graphics.textFont(main.jersey);
        graphics.textAlign(PConstants.CENTER, PConstants.CENTER);

        graphics.textSize(120);
        graphics.text("Player Pandemonium", graphics.width / 2f, graphics.height / 4f);

        graphics.textSize(32);
        graphics.text("Press Enter to Start", graphics.width / 2f, graphics.height / 2f);

        graphics.endDraw();
        return graphics;
    }
}

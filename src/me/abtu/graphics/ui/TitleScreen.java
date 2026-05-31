package me.abtu.graphics.ui;

import me.abtu.Main;
import me.abtu.graphics.GraphicsBuffer;
import processing.core.PConstants;
import processing.core.PGraphics;

public class TitleScreen extends GraphicsBuffer {

    public TitleScreen(int resizeMode) {
        super(resizeMode);
    }

    @Override
    protected void drawBuffer(Main main, PGraphics graphics) {
        graphics.background(255);
        graphics.fill(0);


        final int titleSize = 65;
        final int buttonSize = 20;

        graphics.textFont(main.jersey);
        graphics.textAlign(PConstants.CENTER, PConstants.CENTER);

        graphics.textSize(titleSize);
        graphics.text("Player Pandemonium", graphics.width / 2f, graphics.height / 4f);

        graphics.textSize(buttonSize);
        graphics.text("Press Enter to Start", graphics.width / 2f, graphics.height / 2f);
    }
}

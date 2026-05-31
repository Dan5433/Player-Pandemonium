package me.abtu.graphics.ui;

import me.abtu.Main;
import me.abtu.config.AppConfig;
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
        final int smallSize = 16;
        final int padding = 2;

        graphics.textFont(main.getTitleFont());
        graphics.textAlign(PConstants.CENTER, PConstants.CENTER);

        graphics.textSize(titleSize);
        graphics.text("Player Pandemonium", HALF_WIDTH, QUARTER_HEIGHT);

        graphics.textSize(buttonSize);
        graphics.textFont(main.getDefaultFont());
        graphics.text("Press Enter to Start", HALF_WIDTH, HALF_HEIGHT);

        graphics.textSize(smallSize);
        graphics.textAlign(PConstants.LEFT, PConstants.BOTTOM);
        graphics.text(AppConfig.VERSION, padding, graphics.height);

        graphics.textAlign(PConstants.RIGHT, PConstants.BOTTOM);
        graphics.text(AppConfig.COPYRIGHT, graphics.width - padding, graphics.height);
    }
}

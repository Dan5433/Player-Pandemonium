package me.abtu.graphics.ui;

import me.abtu.Main;
import me.abtu.config.AppConfig;
import me.abtu.graphics.GraphicsBuffer;
import me.abtu.graphics.buttons.Button;
import processing.core.PConstants;
import processing.core.PGraphics;

public class TitleScreen extends GraphicsBuffer {
    protected final Button playButton;
    private int bg = 255;

    public TitleScreen(Main main, int resizeMode) {
        super(main, resizeMode);

        final float width = REFERENCE_WIDTH / 4f;
        final float height = REFERENCE_HEIGHT / 12f;
        playButton = new Button.Builder(HALF_WIDTH, HALF_HEIGHT, width, height, PConstants.CENTER, this::play)
                .text("Play")
                .build();
    }

    public void play() {
        bg--;
    }

    @Override
    protected void drawBuffer(Main main, PGraphics graphics) {
        final float localMouseX = main.mouseX / scaleToScreenX;
        final float localMouseY = main.mouseY / scaleToScreenY;
        playButton.update(localMouseX, localMouseY, main.mousePressed);

        graphics.background(bg);
        graphics.fill(0);

        final int titleSize = 65;
        final int buttonTextSize = 32;
        final int smallTextSize = 16;
        final int padding = 2;

        graphics.textFont(main.getTitleFont());
        graphics.textAlign(PConstants.CENTER, PConstants.CENTER);

        graphics.textSize(titleSize);
        graphics.text("Player Pandemonium", HALF_WIDTH, QUARTER_HEIGHT);

        graphics.textFont(main.getDefaultFont());
        graphics.textSize(buttonTextSize);
        playButton.draw(graphics);

        graphics.textSize(smallTextSize);
        graphics.textAlign(PConstants.LEFT, PConstants.BOTTOM);
        graphics.text(AppConfig.VERSION, padding, graphics.height - padding);

        graphics.textAlign(PConstants.RIGHT, PConstants.BOTTOM);
        graphics.text(AppConfig.COPYRIGHT, graphics.width - padding, graphics.height - padding);
    }
}

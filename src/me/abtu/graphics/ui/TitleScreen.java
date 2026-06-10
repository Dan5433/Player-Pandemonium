package me.abtu.graphics.ui;

import me.abtu.Main;
import me.abtu.config.AppConfig;
import me.abtu.graphics.GraphicsBuffer;
import me.abtu.graphics.buttons.Button;
import processing.core.PConstants;
import processing.core.PGraphics;

public class TitleScreen extends GraphicsBuffer {
    protected final Button playButton;

    public TitleScreen(Main main, String renderer) {
        super(main, renderer);

        final float width = REFERENCE_WIDTH / 4f;
        final float height = REFERENCE_HEIGHT / 12f;
        playButton = new Button.Builder(HALF_WIDTH, HALF_HEIGHT, width, height, PConstants.CENTER, main::openPlayerMenu)
                .text("Play")
                .hoverExpand(2)
                .build();
    }

    @Override
    protected void drawBuffer(Main main, PGraphics graphics, float mouseX, float mouseY) {
        playButton.update(mouseX, mouseY, main.mousePressed);

        final int padding = 2;
        graphics.fill(0);

        graphics.textFont(main.getTitleFont());
        graphics.textAlign(PConstants.CENTER, PConstants.CENTER);

        graphics.textSize(TITLE_SIZE);
        graphics.text("Player Pandemonium", HALF_WIDTH, FIFTH_HEIGHT);

        graphics.textFont(main.getDefaultFont());
        graphics.textSize(BUTTON_TEXT_SIZE);
        playButton.draw(graphics);

        graphics.textSize(SMALL_TEXT_SIZE);
        graphics.textAlign(PConstants.LEFT, PConstants.BOTTOM);
        graphics.text(AppConfig.VERSION, padding, REFERENCE_HEIGHT - padding);

        graphics.textAlign(PConstants.RIGHT, PConstants.BOTTOM);
        graphics.text(AppConfig.COPYRIGHT, REFERENCE_WIDTH - padding, REFERENCE_HEIGHT - padding);
    }
}

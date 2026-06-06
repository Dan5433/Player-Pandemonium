package me.abtu.graphics.ui;

import me.abtu.Main;
import me.abtu.graphics.GraphicsBuffer;
import me.abtu.graphics.buttons.Button;
import processing.core.PConstants;
import processing.core.PGraphics;

public class PauseMenu extends GraphicsBuffer {
    private final Button resumeButton, titleScreenButton;

    public PauseMenu(Main main, int resizeMode) {
        super(main, resizeMode);

        backgroundColor = 0xAA000000;

        final float buttonWidth = REFERENCE_WIDTH / 2.5f;
        final float buttonHeight = REFERENCE_HEIGHT / 10f;
        final float buttonMargin = REFERENCE_HEIGHT / 20f;
        resumeButton = new Button.Builder(HALF_WIDTH, HALF_HEIGHT - buttonHeight / 2f - buttonMargin / 2f, buttonWidth, buttonHeight, PGraphics.CENTER, main::togglePause)
                .text("Resume")
                .hoverExpand(3)
                .build();
        titleScreenButton = new Button.Builder(HALF_WIDTH, HALF_HEIGHT + buttonHeight / 2f + buttonMargin / 2f, buttonWidth, buttonHeight, PGraphics.CENTER, main::exitToTitleScreen)
                .text("Main Menu")
                .hoverExpand(3)
                .build();
    }

    @Override
    protected void drawBuffer(Main main, PGraphics graphics, float mouseX, float mouseY) {
        resumeButton.update(mouseX, mouseY, main.mousePressed);
        titleScreenButton.update(mouseX, mouseY, main.mousePressed);

        graphics.fill(255);

        graphics.textFont(main.getTitleFont());
        graphics.textAlign(PConstants.CENTER, PConstants.CENTER);
        graphics.textSize(TITLE_SIZE);
        graphics.text("Paused", HALF_WIDTH, FIFTH_HEIGHT);

        graphics.textFont(main.getDefaultFont());
        graphics.textSize(BUTTON_TEXT_SIZE);
        resumeButton.draw(graphics);
        titleScreenButton.draw(graphics);
    }
}

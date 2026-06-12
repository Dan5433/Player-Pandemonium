package me.abtu.graphics.game;

import me.abtu.Main;
import me.abtu.graphics.GraphicsBuffer;
import me.abtu.graphics.buttons.Button;
import processing.core.PConstants;
import processing.core.PGraphics;

public class WinScreen extends GraphicsBuffer {
    private final Button replayButton, titleScreenButton;
    private final int winnerNumber;

    public WinScreen(Main main, String renderer, int winnerNumber) {
        super(main, renderer);
        this.winnerNumber = winnerNumber;

        backgroundColor = 0xAA000000;

        final float buttonWidth = REFERENCE_WIDTH / 2.5f;
        final float buttonHeight = REFERENCE_HEIGHT / 10f;
        final float buttonMargin = REFERENCE_HEIGHT / 20f;
        replayButton = new Button.Builder(HALF_WIDTH, HALF_HEIGHT - buttonHeight / 2f - buttonMargin / 2f, buttonWidth, buttonHeight, PGraphics.CENTER, main::togglePause)
                .text("Play Again")
                .hoverExpand(3)
                .build();
        titleScreenButton = new Button.Builder(HALF_WIDTH, HALF_HEIGHT + buttonHeight / 2f + buttonMargin / 2f, buttonWidth, buttonHeight, PGraphics.CENTER, main::exitToTitleScreen)
                .text("Title Screen")
                .hoverExpand(3)
                .build();
    }

    @Override
    protected void drawBuffer(Main main, PGraphics graphics, float mouseX, float mouseY) {
        replayButton.update(mouseX, mouseY, main.mousePressed);
        titleScreenButton.update(mouseX, mouseY, main.mousePressed);

        graphics.fill(255);

        graphics.textFont(main.getTitleFont());
        graphics.textAlign(PConstants.CENTER, PConstants.CENTER);
        graphics.textSize(TITLE_SIZE);
        graphics.text("Player " + winnerNumber + " Wins!", HALF_WIDTH, FIFTH_HEIGHT);

        graphics.textFont(main.getDefaultFont());
        graphics.textSize(BUTTON_TEXT_SIZE);
        replayButton.draw(graphics);
        titleScreenButton.draw(graphics);
    }
}

package me.abtu.graphics.game;

import me.abtu.Main;
import me.abtu.graphics.GraphicsBuffer;
import me.abtu.util.Color;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;

public class MatchCountdown extends GraphicsBuffer {
    public MatchCountdown(Main main, String renderer) {
        super(main, renderer);

        backgroundColor = 0xAA000000;
    }

    @Override
    protected void drawBuffer(Main main, PGraphics graphics, float mouseX, float mouseY) {
        final float countdownSeconds = main.getArena().getCountdownSeconds();
        final int countdownDisplay = PApplet.ceil(countdownSeconds);

        graphics.fill(Color.WHITE.hex());

        graphics.textFont(main.getTitleFont());
        graphics.textSize(TITLE_SIZE);
        graphics.textAlign(PConstants.CENTER, PConstants.CENTER);
        graphics.text(countdownDisplay, HALF_WIDTH, HALF_HEIGHT);
    }
}

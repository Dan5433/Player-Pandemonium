package me.abtu.graphics.ui;

import me.abtu.Main;
import me.abtu.game.entity.player.Player;
import me.abtu.graphics.GraphicsBuffer;
import me.abtu.util.Color;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;

public class PlayerHealth extends GraphicsBuffer {
    public PlayerHealth(Main main, String renderer) {
        super(main, renderer);
        drawBackground = false;
    }

    @Override
    protected void drawBuffer(Main main, PGraphics graphics, float mouseX, float mouseY) {
        graphics.noStroke();
        graphics.fill(0xAA000000);
        graphics.rectMode(PConstants.CORNER);
        final float overlayHeight = REFERENCE_HEIGHT / 9f;
        graphics.rect(0, 0, REFERENCE_WIDTH, overlayHeight);

        //draw health
        final float margin = 30;
        final float statWidth = REFERENCE_WIDTH / 5f;
        final float statHeight = REFERENCE_HEIGHT / 20f;
        Player[] players = main.getPlayers();
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            final float horizontalFraction = (float) i / (players.length - 1);
            final float x = PApplet.lerp(margin, REFERENCE_WIDTH - margin - statWidth, horizontalFraction);

            graphics.fill(0);
            graphics.strokeWeight(3);
            graphics.stroke(0);
            graphics.rect(x, overlayHeight / 2f - statHeight / 2f, statWidth, statHeight);

            final float healthFraction = player.getHealth() / player.getMaxHealth();
            final float width = PApplet.lerp(0, statWidth, Math.clamp(healthFraction, 0, 1));
            graphics.fill(Color.GREEN.hex());
            graphics.rect(x, overlayHeight / 2f - statHeight / 2f, width, statHeight);
        }
    }
}

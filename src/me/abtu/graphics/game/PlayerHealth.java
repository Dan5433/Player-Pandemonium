package me.abtu.graphics.game;

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
        final float statEndMargin = 30;
        final float statWidth = REFERENCE_WIDTH / 5f;
        final float statHeight = REFERENCE_HEIGHT / 20f;
        Player[] players = main.getPlayers();
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            final float horizontalFraction = (float) i / (players.length - 1);
            final float x = PApplet.lerp(statEndMargin, REFERENCE_WIDTH - statEndMargin - statWidth, horizontalFraction);

            //draw black bar background
            graphics.fill(0);
            graphics.strokeWeight(3);
            graphics.stroke(0);
            graphics.rect(x, overlayHeight / 2f - statHeight / 2f, statWidth, statHeight);

            //draw green health bar
            final float healthFraction = player.getHealth() / player.getMaxHealth();
            final float healthBarWidth = PApplet.lerp(0, statWidth, Math.clamp(healthFraction, 0, 1));
            graphics.fill(Color.GREEN.hex());
            graphics.rect(x, overlayHeight / 2f - statHeight / 2f, healthBarWidth, statHeight);

            final float textSidePadding = 3.5f;
            graphics.fill(Color.WHITE.hex());
            graphics.textFont(main.getDefaultFont());
            //draw player index
            graphics.textAlign(PConstants.LEFT, PConstants.CENTER);
            graphics.textSize(SMALL_TEXT_SIZE);
            graphics.text("Player " + (i + 1), x + textSidePadding, overlayHeight / 2f);
            //draw player health
            graphics.textAlign(PConstants.RIGHT, PConstants.CENTER);
            graphics.textSize(SMALL_TEXT_SIZE);
            graphics.text("%#.2f/%#.1f".formatted(player.getHealth(), player.getMaxHealth()),
                    x + statWidth - textSidePadding, overlayHeight / 2f);
        }
    }
}

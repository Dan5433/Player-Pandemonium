package me.abtu.graphics.ui;

import me.abtu.Main;
import me.abtu.game.Player;
import me.abtu.graphics.GraphicsBuffer;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class PlayerMenu extends GraphicsBuffer {

    private final ArrayList<Player> players = new ArrayList<>(2);

    public PlayerMenu(PApplet app, int resizeMode) {
        super(app, resizeMode);

        players.add(new Player(KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_Q, KeyEvent.VK_E));
        players.add(new Player(KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SHIFT, KeyEvent.VK_CONTROL));
    }

    @Override
    protected void drawBuffer(Main main, PGraphics graphics) {
        graphics.fill(0);

        final int titleSize = 65;
        graphics.textFont(main.getTitleFont());
        graphics.textAlign(PConstants.CENTER, PConstants.CENTER);
        graphics.textSize(titleSize);
        graphics.text("Choose Players", HALF_WIDTH, FIFTH_HEIGHT);

        graphics.rectMode(PConstants.CENTER);

        graphics.fill(255);
        final float cardMargin = REFERENCE_WIDTH / 64f;
        final float cardWidth = REFERENCE_WIDTH / 6.4f;
        final float cardHeight = REFERENCE_HEIGHT / 2.4f;
        int numPlayers = players.size();
        for (int i = 0; i < numPlayers; i++) {
            float offset = i - (numPlayers - 1) / 2f;
            float cardX = HALF_WIDTH + offset * (cardWidth + cardMargin);
            graphics.fill(255);
            graphics.rect(cardX, HALF_HEIGHT, cardWidth, cardHeight);


            graphics.translate(cardX, HALF_HEIGHT - cardHeight / 2f);

            graphics.fill(0);
            graphics.textFont(main.getDefaultFont());
            graphics.textSize(SMALL_TEXT_SIZE);

            final float textMargin = 2;
            final float textLineSpacing = 16;
            graphics.textAlign(PConstants.CENTER, PConstants.TOP);
            graphics.text("Player " + (i + 1), 0, textMargin);

            graphics.translate(-cardWidth / 2f, textLineSpacing);
            graphics.textAlign(PConstants.LEFT, PConstants.TOP);
            graphics.text("Jump: " + KeyEvent.getKeyText(players.get(i).jump()), textMargin, textMargin);
            graphics.text("Left: " + KeyEvent.getKeyText(players.get(i).left()), textMargin, textMargin + textLineSpacing);
            graphics.text("Right: " + KeyEvent.getKeyText(players.get(i).right()), textMargin, textMargin + textLineSpacing * 2);
            graphics.text("Primary: " + KeyEvent.getKeyText(players.get(i).primary()), textMargin, textMargin + textLineSpacing * 3);
            graphics.text("Secondary: " + KeyEvent.getKeyText(players.get(i).secondary()), textMargin, textMargin + textLineSpacing * 4);

            graphics.translate(-(cardX - cardWidth / 2f), -(HALF_HEIGHT - cardHeight / 2f + textLineSpacing));
        }
    }
}
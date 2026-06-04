package me.abtu.graphics.game;

import me.abtu.Main;
import me.abtu.game.Player;
import me.abtu.graphics.GraphicsBuffer;
import processing.core.PGraphics;

public class PlayerGraphics extends GraphicsBuffer {

    public PlayerGraphics(Main main, int resizeMode) {
        super(main, resizeMode);
    }

    @Override
    protected void drawBuffer(Main main, PGraphics graphics, float mouseX, float mouseY) {
        for (Player player : main.getPlayers())
            player.draw(graphics);
    }
}

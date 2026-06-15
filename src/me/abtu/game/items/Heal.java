package me.abtu.game.items;

import me.abtu.game.entity.player.Player;
import processing.core.PApplet;
import processing.core.PImage;

public class Heal extends Item {
    private final float healAmount;

    public Heal(float x, float y, PApplet app) {
        super(x, y, new PImage());
        healAmount = app.random(9f, 12f);
    }

    @Override
    public void useItem(Player user) {
        user.heal(healAmount);
    }
}

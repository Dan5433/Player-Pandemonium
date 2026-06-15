package me.abtu.game.items;

import me.abtu.game.entity.player.Player;
import processing.core.PApplet;

public class Heal extends Item {
    private final float healAmount;

    public Heal(float x, float y, PApplet app) {
        super(x, y, app.loadImage("sprites/items/medkit.png"));
        healAmount = app.random(9f, 12f);
    }

    @Override
    public void useItem(Player user) {
        user.heal(healAmount);
    }
}

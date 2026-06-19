package me.abtu.game.items;

import me.abtu.game.entity.player.Player;
import processing.core.PApplet;

public class Heal extends Item {
    private float healAmount;

    public Heal(float x, float y, PApplet app) {
        super(x, y, app.loadImage("sprites/items/medkit.png"));
    }

    @Override
    public void useItem(Player user) {
        user.heal(healAmount);
    }

    @Override
    protected void setInstantiatedProperties(PApplet app) {
        healAmount = app.random(9f, 12f);
    }
}

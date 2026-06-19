package me.abtu.game.items;

import me.abtu.Main;
import me.abtu.game.entity.player.Player;
import processing.core.PApplet;
import processing.core.PImage;

public class Heal extends Item {
    private float healAmount;

    public Heal(PImage sprite) {
        super(sprite);
    }


    @Override
    public void useItem(Player user, Main main) {
        user.heal(healAmount);
    }

    @Override
    protected void setInstantiatedProperties(PApplet app) {
        healAmount = app.random(6f, 10f);
    }
}

package me.abtu.game.items;

import me.abtu.Main;
import me.abtu.game.entity.player.Player;
import processing.core.PApplet;
import processing.core.PImage;

public class Speed extends Item {
    protected float accelerationMultiplier;
    protected float maxSpeedMultiplier;

    public Speed(PImage sprite) {
        super(sprite);
    }

    @Override
    public void useItem(Player user, Main main) {
        user.multiplySpeed(accelerationMultiplier, maxSpeedMultiplier);
    }

    @Override
    protected void setInstantiatedProperties(PApplet app) {
        accelerationMultiplier = app.random(1.025f, 1.05f);
        maxSpeedMultiplier = app.random(1.025f, 1.05f);
    }
}

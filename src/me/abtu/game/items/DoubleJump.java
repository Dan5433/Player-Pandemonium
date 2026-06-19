package me.abtu.game.items;

import me.abtu.Main;
import me.abtu.game.entity.player.Player;
import processing.core.PApplet;
import processing.core.PImage;

public class DoubleJump extends Item {
    public DoubleJump(PImage sprite) {
        super(sprite);
    }

    @Override
    public void useItem(Player user, Main main) {
        user.addDoubleJump();
    }

    @Override
    protected void setInstantiatedProperties(PApplet app) {
    }
}

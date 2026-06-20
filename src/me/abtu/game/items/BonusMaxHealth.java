package me.abtu.game.items;

import me.abtu.Main;
import me.abtu.game.entity.player.Player;
import processing.core.PApplet;
import processing.core.PImage;

public class BonusMaxHealth extends Item {
    private static final int MIN_BONUS = 4;
    private static final int MAX_BONUS = 8;

    protected int bonusMaxHealth;

    public BonusMaxHealth(PImage sprite) {
        super(sprite);
    }

    @Override
    public void useItem(Player user, Main main) {
        user.addMaxHealth(bonusMaxHealth);
    }

    @Override
    protected void setInstantiatedProperties(PApplet app) {
        bonusMaxHealth = (int) app.random(MIN_BONUS, MAX_BONUS + 1); //+1 since random is high exclusive
    }
}

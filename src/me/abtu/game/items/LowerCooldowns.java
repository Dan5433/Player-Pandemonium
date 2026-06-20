package me.abtu.game.items;

import me.abtu.Main;
import me.abtu.game.entity.player.Player;
import me.abtu.game.entity.player.abilities.Ability;
import processing.core.PApplet;
import processing.core.PImage;

public class LowerCooldowns extends Item {
    private float cooldownMultiplier;

    public LowerCooldowns(PImage sprite) {
        super(sprite);
    }

    @Override
    public void useItem(Player user, Main main) {
        for (Ability ability : user.getAbilities()) {
            ability.multiplyCooldown(cooldownMultiplier);
        }
    }

    @Override
    protected void setInstantiatedProperties(PApplet app) {
        cooldownMultiplier = app.random(0.9f, 0.95f);
    }
}

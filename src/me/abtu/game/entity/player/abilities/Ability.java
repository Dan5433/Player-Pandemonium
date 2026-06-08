package me.abtu.game.entity.player.abilities;

import me.abtu.Main;
import me.abtu.game.entity.player.Player;

public abstract class Ability {
    protected float cooldownSeconds;

    public abstract void tryUseAbility(Player player, Main main);

    public void update(float deltaTimeSeconds) {
        cooldownSeconds -= deltaTimeSeconds;
    }
}

package me.abtu.game.entity.player.abilities;

import me.abtu.Main;
import me.abtu.game.entity.player.Player;

public abstract class Ability {
    protected float cooldownSeconds;

    public void tryUseAbility(Player player, Main main) {
        if (cooldownSeconds > 0) //dont use ability if on cooldown
            return;

        useAbility(player, main);
    }

    public abstract void useAbility(Player player, Main main);

    public void update(float deltaTimeSeconds) {
        cooldownSeconds -= deltaTimeSeconds; //subtract seconds passed from cooldown
    }
}

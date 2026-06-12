package me.abtu.game.entity.player.abilities;

import me.abtu.Main;
import me.abtu.game.entity.player.Player;
import processing.sound.SoundFile;

public abstract class Ability {
    protected final SoundFile useSound;
    protected float cooldownSeconds;

    public Ability(SoundFile useSound) {
        this.useSound = useSound;
    }

    public void tryUseAbility(Player player, Main main) {
        if (cooldownSeconds > 0) //dont use ability if on cooldown
            return;

        boolean success = useAbility(player, main);
        if (success)
            useSound.play();
    }

    public abstract boolean useAbility(Player player, Main main);

    public void update(float deltaTimeSeconds) {
        cooldownSeconds -= deltaTimeSeconds; //subtract seconds passed from cooldown
    }
}

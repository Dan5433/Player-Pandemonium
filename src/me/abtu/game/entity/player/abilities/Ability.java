package me.abtu.game.entity.player.abilities;

import me.abtu.Main;
import me.abtu.game.entity.player.Player;
import processing.sound.SoundFile;

public abstract class Ability {
    protected final SoundFile useSound;
    protected float cooldownSecondsTimer;
    protected float cooldownSeconds = getCooldownSeconds();

    public Ability(SoundFile useSound) {
        this.useSound = useSound;
    }

    public void resetCooldown() {
        cooldownSecondsTimer = getCooldownSeconds();
    }

    public void tryUseAbility(Player player, Main main) {
        if (cooldownSecondsTimer > 0) //dont use ability if on cooldown
            return;

        boolean success = useAbility(player, main);
        if (!success)
            return;

        useSound.play();
        cooldownSecondsTimer = cooldownSeconds;
    }

    public abstract boolean useAbility(Player player, Main main);

    public void update(float deltaTimeSeconds) {
        cooldownSecondsTimer -= deltaTimeSeconds; //subtract seconds passed from cooldown
    }

    public void alterCooldown(float multiplier) {
        cooldownSeconds *= multiplier;
    }

    protected abstract float getCooldownSeconds();
}

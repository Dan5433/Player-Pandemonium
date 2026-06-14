package me.abtu.game.entity.player.abilities;

import me.abtu.Main;
import me.abtu.game.entity.player.Player;
import me.abtu.game.entity.projectile.Projectile;
import processing.core.PVector;
import processing.sound.SoundFile;

public class PrimaryAbility extends Ability {
    public PrimaryAbility(SoundFile useSound) {
        super(useSound);
    }

    @Override
    public boolean useAbility(Player player, Main main) {
        int xDirection = player.getLastXInput();
        if (xDirection == 0)
            return false;

        final int projectileSize = 10;
        int xOffset = xDirection * projectileSize * 2;

        final float projectileSpeed = 445.5f;
        final PVector projectileVelocity = new PVector(xDirection * projectileSpeed, 0);
        final int projectileDamage = 5;

        Projectile projectile = new Projectile(player.getX() + xOffset, player.getY(), projectileSize, projectileSize,
                projectileDamage, projectileVelocity, player);
        main.addEntity(projectile);

        cooldownSeconds = 0.3f;
        return true;
    }
}

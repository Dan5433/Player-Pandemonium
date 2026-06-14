package me.abtu.game.entity.player.abilities;

import me.abtu.Main;
import me.abtu.game.entity.player.Player;
import me.abtu.game.entity.projectile.PhysicsProjectile;
import processing.core.PVector;
import processing.sound.SoundFile;

public class SecondaryAbility extends Ability {
    public SecondaryAbility(SoundFile useSound) {
        super(useSound);
    }

    @Override
    public boolean useAbility(Player player, Main main) {
        int xDirection = player.getLastXInput();
        if (xDirection == 0)
            return false;

        final int projectileSize = 20;
        int xOffset = xDirection * projectileSize;

        final PVector projectileVelocity = new PVector(xDirection * 470.5f, -750f);
        final int projectileDamage = 25;

        PhysicsProjectile projectile = new PhysicsProjectile(player.getX() + xOffset, player.getY(), projectileSize, projectileSize,
                projectileDamage, projectileVelocity, player);
        main.addEntity(projectile);

        cooldownSeconds = 1.25f;
        return true;
    }
}

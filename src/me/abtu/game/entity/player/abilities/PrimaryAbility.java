package me.abtu.game.entity.player.abilities;

import me.abtu.Main;
import me.abtu.game.entity.player.Player;
import me.abtu.game.entity.projectile.Projectile;
import processing.core.PVector;

public class PrimaryAbility extends Ability {
    @Override
    public void useAbility(Player player, Main main) {
        int xDirection = player.getLastXInput();
        if (xDirection == 0)
            return;

        final int projectileSize = 10;
        int xOffset = xDirection * projectileSize * 2;

        final float projectileSpeed = 475.5f;
        final PVector projectileVelocity = new PVector(xDirection, 0).mult(projectileSpeed);
        final int projectileDamage = 20;

        Projectile projectile = new Projectile(player.getX() + xOffset, player.getY(), projectileSize, projectileSize,
                projectileDamage, projectileVelocity, player);
        main.addEntity(projectile);

        cooldownSeconds = 0.3f;
    }
}

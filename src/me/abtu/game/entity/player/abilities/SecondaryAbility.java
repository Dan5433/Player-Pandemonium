package me.abtu.game.entity.player.abilities;

import me.abtu.Main;
import me.abtu.game.entity.player.Player;
import me.abtu.game.entity.projectile.PhysicsProjectile;
import processing.core.PVector;
import processing.sound.SoundFile;

public class SecondaryAbility extends Ability {
    private static final float COOLDOWN_SECONDS = 1.25f;

    public SecondaryAbility(SoundFile useSound) {
        super(useSound);
    }

    @Override
    public boolean useAbility(Player player, Main main) {
        int xDirection = player.getLastXInput();
        if (xDirection == 0)
            return false;

        final int projectileSize = 20;
        final float xOffset = xDirection * projectileSize * 1.2f;

        final PVector velocityMinMaxX = new PVector(455.75f, 478.5f);
        final PVector velocityMinMaxY = new PVector(-732.5f, -785.75f);
        final PVector projectileVelocity = new PVector(xDirection * main.random(velocityMinMaxX.x, velocityMinMaxX.y),
                main.random(velocityMinMaxY.x, velocityMinMaxY.y));
        final PVector damageMinMax = new PVector(22f, 31f);
        final float projectileDamage = main.random(damageMinMax.x, damageMinMax.y);

        PhysicsProjectile projectile = new PhysicsProjectile(player.getX() + xOffset, player.getY(), projectileSize, projectileSize,
                projectileDamage, projectileVelocity, player);
        main.addEntity(projectile);
        return true;
    }

    @Override
    protected float getCooldownSeconds() {
        return COOLDOWN_SECONDS;
    }
}

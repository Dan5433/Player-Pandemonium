package me.abtu.game.entity.projectile;

import me.abtu.game.entity.player.Player;
import processing.core.PVector;

public class PhysicsProjectile extends Projectile {
    public PhysicsProjectile(float x, float y, float width, float height, float damage, PVector velocity, Player owner) {
        super(x, y, width, height, damage, velocity, owner);

        gravity = 0.5f;
    }

    @Override
    protected boolean isInAir() {
        return true;
    }
}

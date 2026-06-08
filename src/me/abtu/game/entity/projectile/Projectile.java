package me.abtu.game.entity.projectile;

import me.abtu.Main;
import me.abtu.game.entity.Entity;
import processing.core.PConstants;
import processing.core.PGraphics;

public class Projectile extends Entity {
    private static final int RED = 0xFF_F0_00_00;
    protected final float damage;


    public Projectile(float x, float y, float width, float height, float damage) {
        super(x, y, width, height);
        this.damage = damage;
    }

    @Override
    public void update(Main main) {
    }

    public void draw(PGraphics graphics) {
        graphics.fill(RED);
        graphics.ellipseMode(PConstants.CENTER);
        graphics.ellipse(x, y, width, height);
    }
}

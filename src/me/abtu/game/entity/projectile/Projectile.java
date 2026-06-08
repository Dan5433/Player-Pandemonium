package me.abtu.game.entity.projectile;

import me.abtu.Main;
import me.abtu.game.entity.Entity;
import me.abtu.graphics.GraphicsBuffer;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PVector;

public class Projectile extends Entity {
    private static final int RED = 0xFF_F0_00_00;
    protected final float damage;


    public Projectile(float x, float y, float width, float height, float damage, PVector velocity) {
        super(x, y, width, height);
        this.damage = damage;
        this.velocity = velocity;
    }

    @Override
    public void update(Main main) {
        float deltaTimeSeconds = main.getDeltaTime() / 1000f;
        x += velocity.x * deltaTimeSeconds;
        y += velocity.y * deltaTimeSeconds;

        //despawn if offscreen
        if (x < -width / 2f || x > GraphicsBuffer.REFERENCE_WIDTH + width / 2f || y < -height / 2f || y > GraphicsBuffer.REFERENCE_HEIGHT + height / 2f)
            main.removeEntity(this);
    }

    public void draw(PGraphics graphics) {
        graphics.fill(RED);
        graphics.strokeWeight(0.25f);
        graphics.ellipseMode(PConstants.CENTER);
        graphics.ellipse(x, y, width, height);
    }
}

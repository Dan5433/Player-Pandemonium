package me.abtu.game.entity.projectile;

import me.abtu.Main;
import me.abtu.game.entity.Entity;
import me.abtu.game.entity.player.Player;
import me.abtu.graphics.GraphicsBuffer;
import me.abtu.util.Color;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PVector;

public class Projectile extends Entity {
    protected final float damage;
    protected final Player owner;


    public Projectile(float x, float y, float width, float height, float damage, PVector velocity, Player owner) {
        super(x, y, width, height);
        this.damage = damage;
        this.velocity = velocity;
        this.owner = owner;
    }

    @Override
    public void updateInternal(Main main) {
        float deltaTimeSeconds = main.getDeltaTime() / 1000f;
        x += velocity.x * deltaTimeSeconds;
        y += velocity.y * deltaTimeSeconds;

        //despawn if offscreen
        if (x < -width / 2f || x > GraphicsBuffer.REFERENCE_WIDTH + width / 2f || y < -height / 2f || y > GraphicsBuffer.REFERENCE_HEIGHT + height / 2f) {
            main.removeEntity(this);
            return;
        }

        dealDamageToPlayers(main.getPlayers(), main);
    }

    public void draw(PGraphics graphics) {
        graphics.fill(Color.RED.hex());
        graphics.noStroke();
        graphics.ellipseMode(PConstants.CENTER);
        graphics.ellipse(x, y, width, height);
    }

    private void dealDamageToPlayers(Player[] players, Main main) {
        float topEdge = y - height / 2f;
        float bottomEdge = y + height / 2f;
        float leftEdge = x + width / 2f;
        float rightEdge = x - width / 2f;
        for (Player player : players) {
            if (player == owner) //prevent player who shot projectile being hit
                continue;

            PVector playerTopLeft = player.getTopLeftEdge();
            PVector playerBottomRight = player.getBottomRightEdge();

            boolean withinPlayerY = bottomEdge >= playerTopLeft.y && topEdge <= playerBottomRight.y;
            boolean hitPlayerLeft = rightEdge >= playerTopLeft.x && leftEdge <= playerBottomRight.x;
            boolean hitPlayerRight = leftEdge <= playerBottomRight.x && rightEdge >= playerBottomRight.x;

            if (withinPlayerY && (hitPlayerLeft || hitPlayerRight)) {
                player.dealDamage(damage);
                main.removeEntity(this);
                return;
            }
        }
    }
}

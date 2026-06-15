package me.abtu.game.entity.projectile;

import me.abtu.Main;
import me.abtu.game.entity.PhysicsEntity;
import me.abtu.game.entity.player.Player;
import me.abtu.graphics.GraphicsBuffer;
import me.abtu.util.Color;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PVector;

public class Projectile extends PhysicsEntity {
    protected final float damage;
    protected final Player owner;


    public Projectile(float x, float y, float width, float height, float damage, PVector velocity, Player owner) {
        super(x, y, width, height);
        this.damage = damage;
        this.velocity = velocity;
        this.owner = owner;

        maxHorizontalVelocity = Math.abs(velocity.x);
    }

    @Override
    public void updateInternal(Main main) {
        dealDamageToPlayers(main.getPlayers(), main);

        final boolean beyondLeftEdge = x < -width / 2f;
        final boolean beyondRightEdge = x > GraphicsBuffer.REFERENCE_WIDTH + width / 2f;
        final boolean beyondTopEdge = y < -height / 2f;
        final boolean beyondBottomEdge = y > GraphicsBuffer.REFERENCE_HEIGHT + height / 2f;
        //despawn if offscreen
        if (beyondLeftEdge || beyondRightEdge || beyondTopEdge || beyondBottomEdge)
            main.removeEntity(this);
    }

    public void draw(PGraphics graphics) {
        graphics.fill(Color.RED.hex());
        graphics.noStroke();
        graphics.ellipseMode(PConstants.CENTER);
        graphics.ellipse(x, y, width, height);
    }

    private void dealDamageToPlayers(Player[] players, Main main) {
        final float topEdge = y - height / 2f;
        final float bottomEdge = y + height / 2f;
        final float leftEdge = x - width / 2f;
        final float rightEdge = x + width / 2f;
        final float previousFrameLeftEdge = previousFrameX - width / 2f;
        final float previousFrameRightEdge = previousFrameX + width / 2f;
        final float previousFrameTopEdge = previousFrameY - height / 2f;
        final float previousFrameBottomEdge = previousFrameY + height / 2f;
        for (Player player : players) {
            if (player.isDead()) //skip dead players
                continue;

            if (player == owner) //prevent player who shot projectile being hit
                continue;

            PVector playerTopLeft = player.getTopLeftEdge();
            PVector playerBottomRight = player.getBottomRightEdge();
            final float playerPreviousX = player.getPreviousFrameX();
            final float playerPreviousY = player.getPreviousFrameY();

            //check hit by comparing positions of previous and current frame
            //counts as hit if projectile went through player
            //prevents projectile from phasing through player on large game steps
            final boolean withinPlayerY = bottomEdge >= playerTopLeft.y && topEdge <= playerBottomRight.y;
            final boolean hitPlayerTop = bottomEdge >= playerTopLeft.y && previousFrameTopEdge <= playerPreviousY;
            final boolean hitPlayerBottom = topEdge <= playerBottomRight.y && previousFrameBottomEdge >= playerPreviousY;
            final boolean hitPlayerLeft = rightEdge >= playerTopLeft.x && previousFrameLeftEdge <= playerPreviousX;
            final boolean hitPlayerRight = leftEdge <= playerBottomRight.x && previousFrameRightEdge >= playerPreviousX;

            if ((withinPlayerY || hitPlayerTop || hitPlayerBottom) && (hitPlayerLeft || hitPlayerRight)) {
                player.dealDamage(damage);
                main.removeEntity(this);
                return;
            }
        }
    }

    @Override
    protected boolean shouldApplyFriction() {
        return false; //projectiles don't have air friction
    }

    @Override
    protected boolean isInAir() {
        return false;
    }
}

package me.abtu.game.entity;

import me.abtu.Main;
import me.abtu.graphics.GraphicsBuffer;

public abstract class PhysicsEntity extends Entity {
    //unscaled
    protected float maxHorizontalVelocity = 5.5f;
    protected float terminalVelocity = 10f;
    //scaled for delta time
    protected float friction = 7.5f;
    protected float gravity = 0.75f;


    public PhysicsEntity(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public void update(Main main) {
        previousFrameX = x;
        previousFrameY = y;

        final float deltaTimeSeconds = main.getDeltaTime() / 1000f;
        updateVelocity(deltaTimeSeconds);

        x += velocity.x;
        y += velocity.y;

        updateInternal(main);
    }

    protected void updateVelocity(float deltaTimeSeconds) {
        //update x velocity
        if (shouldApplyFriction()) {
            float frictionFactor = 1 - friction * deltaTimeSeconds;
            frictionFactor = Math.clamp(frictionFactor, 0, 1); //ensure friction doesnt cause velocity to invert on high delta time values
            velocity.x *= frictionFactor;

            //set velocity to 0 if very small
            if (Math.abs(velocity.x) < 0.01f)
                velocity.x = 0;
        }

        velocity.x = Math.clamp(velocity.x, -maxHorizontalVelocity, maxHorizontalVelocity);

        //update y velocity
        if (isInAir())
            velocity.y += 1 + gravity * deltaTimeSeconds;
        else
            velocity.y = 0;


        if (velocity.y > terminalVelocity)
            velocity.y = terminalVelocity;
    }

    protected boolean isInAir() {
        return y < GraphicsBuffer.REFERENCE_HEIGHT - height / 2f;
    }

    protected abstract boolean shouldApplyFriction();
}

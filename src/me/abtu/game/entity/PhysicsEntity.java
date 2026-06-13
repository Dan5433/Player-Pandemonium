package me.abtu.game.entity;

import me.abtu.Main;
import me.abtu.graphics.GraphicsBuffer;

public abstract class PhysicsEntity extends Entity {
    //unscaled
    protected float maxHorizontalVelocity = 50f;
    protected float terminalVelocity = 435f;
    //scaled for delta time
    protected float friction = 7.5f;
    protected float gravity = 1450.75f;


    public PhysicsEntity(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public void update(Main main) {
        previousFrameX = x;
        previousFrameY = y;

        final float deltaTimeSeconds = main.getDeltaTime() / 1000f;
        updateVelocity(deltaTimeSeconds);

        x += velocity.x * deltaTimeSeconds;
        y += velocity.y * deltaTimeSeconds;

        updateInternal(main);
    }

    protected void updateVelocity(float deltaTimeSeconds) {
        //update x velocity
        if (shouldApplyFriction()) {
            //apply delta time scaled friction
            float frictionFactor = 1 - friction * deltaTimeSeconds;
            frictionFactor = Math.clamp(frictionFactor, 0, 1); //ensure friction doesnt cause velocity to invert on high delta time values
            velocity.x *= frictionFactor;

            //set velocity to 0 if very small
            if (Math.abs(velocity.x) < 0.01f)
                velocity.x = 0;
        }

        //clamp velocity to maximum speed
        velocity.x = Math.clamp(velocity.x, -maxHorizontalVelocity, maxHorizontalVelocity);

        //update y velocity
        if (isInAir())
            //apply delta time scaled gravity if in air
            velocity.y += gravity * deltaTimeSeconds;
        else
            velocity.y = 0; //reset y velocity if not in air


        //clamp gravity velocity
        if (velocity.y > terminalVelocity)
            velocity.y = terminalVelocity;
    }

    protected boolean isInAir() {
        return y < GraphicsBuffer.REFERENCE_HEIGHT - height / 2f;
    }

    protected abstract boolean shouldApplyFriction();
}

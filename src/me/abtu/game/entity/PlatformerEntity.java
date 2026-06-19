package me.abtu.game.entity;

import me.abtu.Main;
import me.abtu.game.environment.Platform;
import me.abtu.graphics.GraphicsBuffer;

public abstract class PlatformerEntity extends PhysicsEntity {
    private boolean isOnPlatform;

    public PlatformerEntity(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    protected void updateInternal(Main main) {
        //dont let platformers go off screen
        x = Math.clamp(x, width / 2f, GraphicsBuffer.REFERENCE_WIDTH - width / 2f);
        y = Math.clamp(y, -height / 2f, GraphicsBuffer.REFERENCE_HEIGHT - height / 2f);

        platformCheck(main.getArena().getPlatforms());
    }

    private void platformCheck(Platform[] platforms) {
        final float leftEdge = x - width / 2f;
        final float rightEdge = x + width / 2f;
        final float bottomEdge = y + height / 2f;
        final float previousFrameBottomEdge = previousFrameY + height / 2f;

        //check if entity should be on a platform if it was above it last frame and is now at or below it
        for (Platform platform : platforms) {
            if (platform.canObjectStandOn(leftEdge, rightEdge,
                    bottomEdge, previousFrameBottomEdge, velocity.y)) {
                isOnPlatform = true;

                //set y to platform top
                y = platform.getTopSurfaceY() - height / 2f;
                return;
            }
        }

        isOnPlatform = false;
    }

    @Override
    protected boolean isInAir() {
        return super.isInAir() && !isOnPlatform;
    }
}

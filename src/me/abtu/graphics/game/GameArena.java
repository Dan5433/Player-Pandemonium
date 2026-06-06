package me.abtu.graphics.game;

import me.abtu.Main;
import me.abtu.game.Platform;
import me.abtu.graphics.GraphicsBuffer;
import processing.core.PGraphics;

public class GameArena extends GraphicsBuffer {
    protected Platform[] platforms;

    public GameArena(Main main, int resizeMode) {
        super(main, resizeMode);

        final float centerPlatformWidth = REFERENCE_WIDTH / 5f;
        final float smallPlatformWidth = REFERENCE_WIDTH / 8f;
        final float platformHeight = REFERENCE_HEIGHT / 20f;
        final int cyan = 0xFF00F0F0;
        platforms = new Platform[]{
                //lowest middle platform
                new Platform(HALF_WIDTH, REFERENCE_HEIGHT - REFERENCE_HEIGHT / 10f, centerPlatformWidth, platformHeight, cyan),
                //lowest 2 small platforms
                new Platform(REFERENCE_WIDTH / 4f, REFERENCE_HEIGHT - REFERENCE_HEIGHT / 4f, smallPlatformWidth, platformHeight, cyan),
                new Platform(REFERENCE_WIDTH * 3f / 4f, REFERENCE_HEIGHT - REFERENCE_HEIGHT / 4f, smallPlatformWidth, platformHeight, cyan),
                //2 outermost small platforms
                new Platform(REFERENCE_WIDTH / 8f, HALF_HEIGHT + platformHeight, smallPlatformWidth, platformHeight, cyan),
                new Platform(REFERENCE_WIDTH * 7f / 8f, HALF_HEIGHT + platformHeight, smallPlatformWidth, platformHeight, cyan),
                //center platform
                new Platform(HALF_WIDTH, HALF_HEIGHT, centerPlatformWidth, platformHeight, cyan),
                //highest 2 small platforms
                new Platform(REFERENCE_WIDTH * 3f / 10f, HALF_HEIGHT - FIFTH_HEIGHT, smallPlatformWidth, platformHeight, cyan),
                new Platform(REFERENCE_WIDTH * 7f / 10f, HALF_HEIGHT - FIFTH_HEIGHT, smallPlatformWidth, platformHeight, cyan),
        };

        backgroundColor = 0x00FFFFFF;
    }


    @Override
    protected void drawBuffer(Main main, PGraphics graphics, float mouseX, float mouseY) {
        for (Platform platform : platforms)
            platform.draw(graphics);
    }

    public Platform[] getPlatforms() {
        return platforms;
    }
}

package me.abtu.graphics.game;

import me.abtu.Main;
import me.abtu.game.environment.Platform;
import me.abtu.game.items.Item;
import me.abtu.graphics.GraphicsBuffer;
import me.abtu.util.Color;
import me.abtu.util.ItemManager;
import processing.core.PGraphics;

public class GameArena extends GraphicsBuffer {
    protected Platform[] platforms;

    private float countdownSeconds = 3f;
    private float itemSpawnCooldown;

    public GameArena(Main main, String renderer) {
        super(main, renderer);

        final float centerPlatformWidth = REFERENCE_WIDTH / 5f;
        final float smallPlatformWidth = REFERENCE_WIDTH / 8f;
        final float platformHeight = REFERENCE_HEIGHT / 20f;
        final int cyan = Color.CYAN.hex();
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

        drawBackground = false;
    }


    @Override
    protected void drawBuffer(Main main, PGraphics graphics, float mouseX, float mouseY) {
        for (Platform platform : platforms)
            platform.draw(graphics);
    }

    public void updateItemSpawning(float deltaTimeSeconds, Main main) {
        itemSpawnCooldown -= deltaTimeSeconds;
        if (itemSpawnCooldown > 0)
            return;

        //spawn item if not on cooldown
        Item[] itemPool = ItemManager.getItems();
        int randomIndex = (int) main.random(itemPool.length);
        Item prefab = itemPool[randomIndex];

        float x = main.random(prefab.getWidth() / 2f, REFERENCE_WIDTH - prefab.getWidth() / 2f);
        float y = prefab.getHeight() / 2f;
        Item item = prefab.instantiate(x, y, main);

        main.addEntity(item);
        setItemSpawnCooldown(main);
    }

    public void setItemSpawnCooldown(Main main) {
        int playerCount = main.getPlayers().length;
        itemSpawnCooldown = main.random(6f / playerCount, 10f / playerCount); //3 to 5 second cooldown on 2 player
    }

    public float updateCountdown(float deltaTimeSeconds) {
        countdownSeconds -= deltaTimeSeconds;
        return countdownSeconds;
    }

    public Platform[] getPlatforms() {
        return platforms;
    }

    public float getCountdownSeconds() {
        return countdownSeconds;
    }

    public void resetForRematch() {
        countdownSeconds = 3f;
    }
}

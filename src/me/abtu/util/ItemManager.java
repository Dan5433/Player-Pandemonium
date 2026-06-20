package me.abtu.util;

import me.abtu.game.items.*;
import processing.core.PApplet;

public final class ItemManager {
    private static Item[] items;

    public static void initialize(PApplet app) {
        items = new Item[]{
                new Heal(app.loadImage("sprites/items/medkit.png")),
                new Bomb(app.loadImage("sprites/items/bomb.png")),
                new LowerCooldowns(app.loadImage("sprites/items/bullets.png")),
                new DoubleJump(app.loadImage("sprites/items/cloud_up.png")),
                new BonusMaxHealth(app.loadImage("sprites/items/heart_plus.png")),
                new Speed(app.loadImage("sprites/items/blue_arrow.png")),
        };
    }

    public static Item[] getItems() {
        return items;
    }
}

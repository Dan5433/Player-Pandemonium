package me.abtu.util;

import me.abtu.game.items.Bomb;
import me.abtu.game.items.Heal;
import me.abtu.game.items.Item;
import me.abtu.game.items.LowerCooldowns;
import processing.core.PApplet;

public final class ItemManager {
    private static Item[] items;

    public static void initialize(PApplet app) {
        items = new Item[]{
                new Heal(app.loadImage("sprites/items/medkit.png")),
                new Bomb(app.loadImage("sprites/items/bomb.png")),
                new LowerCooldowns(app.loadImage("sprites/items/bullets.png")),
        };
    }

    public static Item[] getItems() {
        return items;
    }
}

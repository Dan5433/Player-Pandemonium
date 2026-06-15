package me.abtu.util;

import me.abtu.game.items.Heal;
import me.abtu.game.items.Item;
import processing.core.PApplet;

public final class ItemManager {
    private static Item[] items;

    public static void initialize(PApplet app) {
        items = new Item[]{
                new Heal(0, 0, app),
        };
    }

    public static Item[] getItems() {
        return items;
    }
}

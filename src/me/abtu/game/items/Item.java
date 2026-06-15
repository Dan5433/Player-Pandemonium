package me.abtu.game.items;

import me.abtu.Main;
import me.abtu.game.entity.PhysicsEntity;
import me.abtu.game.entity.player.Player;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

public abstract class Item extends PhysicsEntity {
    private final PImage sprite;

    public Item(float x, float y, PImage sprite) {
        super(x, y, sprite.width, sprite.height);
        this.sprite = sprite;
    }

    public abstract void useItem(Player user);

    @Override
    public void draw(PGraphics graphics) {
        graphics.imageMode(PConstants.CENTER);
        graphics.image(sprite, x, y, width, height);
    }

    @Override
    protected boolean shouldApplyFriction() {
        return false;
    }

    @Override
    protected void updateInternal(Main main) {
        //implement use item on collision
    }
}

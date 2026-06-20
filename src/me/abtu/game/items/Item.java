package me.abtu.game.items;

import me.abtu.Main;
import me.abtu.game.entity.PlatformerEntity;
import me.abtu.game.entity.player.Player;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

public abstract class Item extends PlatformerEntity implements Cloneable {
    private final PImage sprite;

    public Item(PImage sprite) {
        super(0, 0, sprite.width, sprite.height);
        this.sprite = sprite;

        terminalVelocity = 100f;
        gravity = 500f;
    }

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
        super.updateInternal(main);

        for (Player player : main.getPlayers()) {
            if (player.isDead()) //skip dead players
                continue;

            if (collidedWith(player)) {
                useItem(player, main);
                main.removeEntity(this);
                return;
            }
        }
    }

    public Item instantiate(float x, float y, PApplet app) {
        Item clone = clone();
        clone.x = x;
        clone.y = y;
        clone.setInstantiatedProperties(app);
        return clone;
    }

    public abstract void useItem(Player user, Main main);

    protected abstract void setInstantiatedProperties(PApplet app);

    @Override
    public Item clone() {
        return (Item) super.clone();
    }
}

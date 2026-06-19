package me.abtu.game.items;

import me.abtu.Main;
import me.abtu.game.entity.PhysicsEntity;
import me.abtu.game.entity.player.Player;
import me.abtu.graphics.GraphicsBuffer;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

public abstract class Item extends PhysicsEntity implements Cloneable {
    private final PImage sprite;

    public Item(float x, float y, PImage sprite) {
        super(x, y, sprite.width, sprite.height);
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
        //dont let items go off screen
        x = Math.clamp(x, width / 2f, GraphicsBuffer.REFERENCE_WIDTH - width / 2f);
        y = Math.clamp(y, -height / 2f, GraphicsBuffer.REFERENCE_HEIGHT - height / 2f);

        for (Player player : main.getPlayers()) {
            if (player.isDead()) //skip dead players
                continue;

            if (collidedWith(player)) {
                useItem(player);
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

    public abstract void useItem(Player user);

    protected abstract void setInstantiatedProperties(PApplet app);

    @Override
    public Item clone() {
        return (Item) super.clone();
    }
}

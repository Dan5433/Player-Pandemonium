package me.abtu.game.items;

import me.abtu.Main;
import me.abtu.game.entity.player.Player;
import processing.core.PApplet;

public class Bomb extends Item {
    private static final float EXPLOSION_RADIUS = 100f; //in pixels

    private float maxDamage;

    public Bomb(PApplet app) {
        super(app.loadImage("sprites/items/bomb.png"));
    }

    @Override
    protected void updateInternal(Main main) {
        super.updateInternal(main);

        //explode if it lands on something
        if (!isInAir()) {
            dealAreaDamage(null, main);
            main.removeEntity(this);
        }
    }

    @Override
    public void useItem(Player user, Main main) {
        user.dealDamage(maxDamage);

        dealAreaDamage(user, main);
    }

    private void dealAreaDamage(Player user, Main main) {
        for (Player player : main.getPlayers()) {
            if (player == user) //skip person who touched it and got max damage
                continue;

            float distance = PApplet.dist(x, y, player.getX(), player.getY());
            float damageMagnitude = 1f - PApplet.pow(distance / EXPLOSION_RADIUS, 2);

            float damage = PApplet.lerp(0, maxDamage, damageMagnitude);
            if (damage > 0)
                player.dealDamage(damage);
        }
    }

    @Override
    protected void setInstantiatedProperties(PApplet app) {
        maxDamage = app.random(8f, 14f);
    }
}

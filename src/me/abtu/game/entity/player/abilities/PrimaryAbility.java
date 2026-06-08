package me.abtu.game.entity.player.abilities;

import me.abtu.Main;
import me.abtu.game.entity.player.Player;
import me.abtu.game.entity.projectile.Projectile;

public class PrimaryAbility extends Ability {
    @Override
    public void tryUseAbility(Player player, Main main) {
        if (cooldownSeconds > 0)
            return;

        Projectile projectile = new Projectile(player.getX(), player.getY(), 10, 10, 20);
        main.addEntity(projectile);

        cooldownSeconds = 10;
    }


}

package fr.badblock.bukkit.hub.v2.listeners.players;

import fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets.EnderPearlGun;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileHitListener extends BadListener {

    @EventHandler
    public void onProjectileHitEvent(ProjectileHitEvent event) {

        if (event.getEntity().getShooter() instanceof Player) {
            BadblockPlayer player = (BadblockPlayer) event.getEntity().getShooter();
            HubPlayer hubPlayer = HubPlayer.get(player);

            if (hubPlayer != null && hubPlayer.getCurrentWidget() != null &&
                    (hubPlayer.getCurrentWidget().getClass().getSimpleName().equals(EnderPearlGun.class.getSimpleName()))) {
                if (event.getEntity() instanceof EnderPearl) {
                    hubPlayer.getCurrentWidget().handleInteraction(player, event.getEntity());
                }
            }
        }
    }

}

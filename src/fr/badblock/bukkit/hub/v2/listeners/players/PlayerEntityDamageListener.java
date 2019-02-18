package fr.badblock.bukkit.hub.v2.listeners.players;

import fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets.AbstractGadgets;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets.Nuke;
import fr.badblock.bukkit.hub.v2.games.gladiators.GladiatorManager;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;

public class PlayerEntityDamageListener extends BadListener {

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        if (event.getEntity() instanceof Player && event.getTarget() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (GladiatorManager.getInstance().getCustomInv().containsKey(player))
                return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onDamaged(EntityDamageByEntityEvent event) {

        if (event.getDamager() instanceof TNTPrimed && event.getEntity() instanceof Player) {
            BadblockPlayer player = (BadblockPlayer) event.getEntity();
            HubPlayer hubPlayer = HubPlayer.get(player);

            if (hubPlayer != null && hubPlayer.getCurrentWidget() != null &&
                    (hubPlayer.getCurrentWidget().getClass().getSimpleName().equals(Nuke.class.getSimpleName()))) {
                hubPlayer.getCurrentWidget().handleInteraction(event.getDamager(), event.getEntity());
                event.setCancelled(true);
            }

        } else if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            BadblockPlayer player = (BadblockPlayer) event.getDamager();
            HubPlayer hubDamager = HubPlayer.get(player);
            HubPlayer hubEntity = HubPlayer.get((BadblockPlayer) event.getEntity());

            if (GladiatorManager.getInstance().getCustomInv().containsKey(player))
                return;

            if (hubDamager != null) {
                event.setCancelled(true);
                if (hubDamager.getCurrentWidget() != null) {
                    AbstractGadgets gadgets = hubDamager.getCurrentWidget();
                    gadgets.handleInteraction(event.getDamager(), event.getEntity());
                } else if (hubEntity.getCurrentWidget() != null)
                    hubEntity.getCurrentWidget().handleInteraction(event.getDamager(), event.getEntity());
            }

        } else if (event.getEntity() instanceof Player && !(event.getDamager() instanceof Player)) {
            event.setCancelled(true);
            if (event.getDamager().hasMetadata("owner-id")) {
                String playerName = event.getDamager().getMetadata("owner-id").get(0).asString();
                HubPlayer hubPlayer = HubPlayer.get(playerName);

                if (hubPlayer == null)
                    return;

                if (hubPlayer.getCurrentWidget() != null) {
                    hubPlayer.getCurrentWidget().handleInteraction(event.getEntity(), event.getDamager());
                }
            }
        }

    }

}

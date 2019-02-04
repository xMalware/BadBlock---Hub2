package fr.badblock.bukkit.hub.v2.listeners.players;

import fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets.AbstractGadgets;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerEntityDamageListener extends BadListener {

    @EventHandler
    public void onDamaged(EntityDamageByEntityEvent event) {
        event.setCancelled(true);

        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            BadblockPlayer player = (BadblockPlayer) event.getDamager();
            HubPlayer hubDamager = HubPlayer.get(player);
            HubPlayer hubEntity = HubPlayer.get((BadblockPlayer) event.getEntity());

            if(hubDamager != null){
                if (hubDamager.getCurrentWidget() != null) {
                    AbstractGadgets gadgets = hubDamager.getCurrentWidget();
                    gadgets.handleInteraction(event.getDamager(), event.getEntity());
                    return;
                } else if (hubEntity.getCurrentWidget() != null) {
                    hubEntity.getCurrentWidget().handleInteraction(event.getDamager(), event.getEntity());
                    return;
                }
            }

        } else if (event.getDamager() instanceof Player && !(event.getEntity() instanceof Player)) {
            if (event.getEntity().hasMetadata("owner-id")) {
                String playerName = event.getEntity().getMetadata("owner-id").get(0).asString();
                HubPlayer hubPlayer = HubPlayer.get(playerName);

                if(hubPlayer == null)
                    return;

                if(hubPlayer.getCurrentWidget() != null){
                    hubPlayer.getCurrentWidget().handleInteraction(event.getDamager(), event.getEntity());
                }
            }
        }

    }

}

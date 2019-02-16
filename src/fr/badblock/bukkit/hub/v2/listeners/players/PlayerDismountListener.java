package fr.badblock.bukkit.hub.v2.listeners.players;

import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.event.EventHandler;
import org.spigotmc.event.entity.EntityDismountEvent;

public class PlayerDismountListener extends BadListener {

    @EventHandler
    public void onDismount(EntityDismountEvent event){
        if(event.getEntity() instanceof BadblockPlayer){
            BadblockPlayer badblockPlayer = (BadblockPlayer) event.getEntity();
            HubPlayer hubPlayer = HubPlayer.get(badblockPlayer);

            if(hubPlayer.getMountEntity() != null && hubPlayer.getMountEntity().isValid()){
                event.getDismounted().remove();
            }

        }


    }

}

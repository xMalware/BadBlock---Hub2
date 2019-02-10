package fr.badblock.bukkit.hub.v2.listeners.players;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.CustomDisguiseEffect;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.players.BadblockPlayer;

public class PlayerMoveListener extends BadListener {


    @EventHandler
    public void whenPlayerMoved(PlayerMoveEvent event) {
        BadblockPlayer player = (BadblockPlayer) event.getPlayer();
        HubPlayer hubPlayer = HubPlayer.get(player);

        workWithDisguiseEffects(player, hubPlayer);
        workLaunchpad(player, event.getTo());
        autoTeleport(player, event);
    }

    private void autoTeleport(BadblockPlayer player, PlayerMoveEvent event) {
        Location to = event.getTo();
        if (to.getY() <= 0) {
            event.setTo(to.getWorld().getSpawnLocation());
        }
    }

    private void workLaunchpad(BadblockPlayer player, Location to) {
        Location loc2 = to.clone().add(0, -1, 0);
        Location loc3 = to.clone().add(0, -2, 0);
        Block block1 = loc2.getBlock();
        Block block2 = loc3.getBlock();
        if (isLaunchpadBlock(block1) || isLaunchpadBlock(block2)) {
            player.setVelocity(player.getLocation().getDirection().multiply(20).add(new Vector(0.5, 3, 0.5)));
        }
    }

    @SuppressWarnings("deprecation")
    private boolean isLaunchpadBlock(Block block) {
        return block.getType().equals(Material.WOOL) && block.getData() == 10;
    }

    private void workWithDisguiseEffects(BadblockPlayer player, HubPlayer hubPlayer) {
        // Not disguised
        if (!player.isDisguised()) {
            return;
        }
        // Null custom disguise
        if (hubPlayer.getDisguise() == null) {
            return;
        }
        CustomDisguiseEffect customDisguiseEffect = hubPlayer.getDisguise().getEffect();
        // No effect, we don't do anything more about it
        if (customDisguiseEffect == null) {
            return;
        }
        //player.playEffect(player.getLocation(), customDisguiseEffect.build(), 0);
    }

}

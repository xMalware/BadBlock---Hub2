package fr.badblock.bukkit.hub.v2.listeners.players;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets.EnderPearlGun;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleportListener extends BadListener {

    private ImmutableSet<Material> blockedBlocks;

    public PlayerTeleportListener() {
        this.blockedBlocks = Sets.immutableEnumSet(Material.THIN_GLASS,
                Material.IRON_FENCE, Material.FENCE, Material.NETHER_FENCE,
                Material.FENCE_GATE, Material.ACACIA_STAIRS, Material.BIRCH_WOOD_STAIRS, Material.BRICK_STAIRS,
                Material.COBBLESTONE_STAIRS, Material.DARK_OAK_STAIRS, Material.JUNGLE_WOOD_STAIRS,
                Material.NETHER_BRICK_STAIRS, Material.QUARTZ_STAIRS, Material.SANDSTONE_STAIRS,
                Material.SMOOTH_STAIRS, Material.SPRUCE_WOOD_STAIRS, Material.WOOD_STAIRS);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        BadblockPlayer player = (BadblockPlayer) event.getPlayer();
        HubPlayer hubPlayer = HubPlayer.get(player);

        if (hubPlayer != null && hubPlayer.getCurrentWidget() != null &&
                (hubPlayer.getCurrentWidget().getClass().getSimpleName().equals(EnderPearlGun.class.getSimpleName()))) {
            Location to = event.getTo();

            event.setCancelled(true);
            to.setX(to.getBlockX() + 0.5D);
            to.setZ(to.getBlockZ() + 0.5D);
            event.setTo(to);
        }
    }

}

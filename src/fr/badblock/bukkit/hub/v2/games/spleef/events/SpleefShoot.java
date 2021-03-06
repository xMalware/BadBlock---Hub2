package fr.badblock.bukkit.hub.v2.games.spleef.events;

import fr.badblock.bukkit.hub.v2.games.spleef.SpleefManager;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.util.BlockIterator;


public class SpleefShoot implements Listener {

    /*@EventHandler
    public void ProjectileHitEvent(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball && event.getEntity().getShooter() instanceof Player) {
            Player player = (Player) event.getEntity().getShooter();

            if (!SpleefManager.getInstance().getSpleefPlayers().containsKey(player) || !(SpleefManager.getInstance().getGameState() == GameState.INGAME))
                return;

            BlockIterator iterator = new BlockIterator(event.getEntity().getWorld(), event.getEntity().getLocation().toVector(), event.getEntity().getVelocity().normalize(), 0.0D, 4);
            Block hitBlock = null;

            Location blockloc;
            while (iterator.hasNext()) {
                hitBlock = iterator.next();
                if (hitBlock.getType() != Material.SNOW_BLOCK) {
                    blockloc = hitBlock.getLocation();
                    Block block = blockloc.getBlock();
                    block.setTypeIdAndData(hitBlock.getTypeId(), hitBlock.getData(), true);
                } else if (hitBlock.getType() == Material.SNOW_BLOCK) {
                    SpleefBreak.addBlock(hitBlock.getLocation());
                    break;
                }
            }

            blockloc = hitBlock.getLocation();
            blockloc.setX(blockloc.getX() + 0.5D);
            blockloc.setY(blockloc.getY() + 0.5D);
            blockloc.setZ(blockloc.getZ() + 0.5D);
            hitBlock.setType(Material.AIR);
        }
    }*/

    @EventHandler
    public void onEggLand(PlayerEggThrowEvent event)
    {
        BlockIterator blockIterator = new BlockIterator(event.getEgg().getWorld(), event.getEgg().getLocation().toVector(), event.getEgg().getVelocity().normalize(), 0.0D, 4);
        Block hitBlock = null;
        while (blockIterator.hasNext())
        {
            hitBlock = blockIterator.next();
            if (hitBlock.getType() != Material.AIR)
                break;
        }

        if (hitBlock != null && hitBlock.getType() == Material.SNOW_BLOCK){
            hitBlock.setType(Material.AIR);
            SpleefBreak.addBlock(hitBlock.getLocation());
        }

        event.setHatching(false);
        event.setNumHatches((byte)0);
    }

}

package fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import fr.badblock.gameapi.players.BadblockPlayer;

public class Nuke extends AbstractGadgets {

    public Nuke(String internalName) {
        super(internalName, new ItemStack(Material.TNT), 4);
    }

    @Override
    public void equip(BadblockPlayer badblockPlayer) {

    }

    @Override
    public void unequip(BadblockPlayer badblockPlayer) {

    }

    @Override
    public boolean use(BadblockPlayer badblockPlayer, ItemStack item, Action action) {
        Location location = badblockPlayer.getLocation().add(0, 1, 0);

        for (int i = 0; i <= 10; i++) {
            TNTPrimed tntPrimed = (TNTPrimed) badblockPlayer.getWorld().spawnEntity(location, EntityType.PRIMED_TNT);
            tntPrimed.setFuseTicks(32);
        }


        return true;
    }

    @Override
    public void handleInteraction(Entity from, Entity to) {

        if(from instanceof TNTPrimed && to instanceof BadblockPlayer){
            TNTPrimed tntPrimed = (TNTPrimed) from;
            BadblockPlayer player = (BadblockPlayer) to;

            for (Entity entity : tntPrimed.getNearbyEntities(2, 2, 2)) {
                if (entity instanceof Player) {
                    Player p = (Player) entity;
                    if (player.equals(p)) {
                        player.setVelocity(player.getLocation().getDirection().multiply(2).setY(2));
                    }

                }
            }
        }
    }

    @Override
    public int waitingTime() {
        return 10000;
    }
}

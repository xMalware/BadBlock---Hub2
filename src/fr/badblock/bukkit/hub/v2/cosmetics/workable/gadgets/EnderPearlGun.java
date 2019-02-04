package fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets;

import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class EnderPearlGun extends AbstractGadgets{

    public EnderPearlGun() {
        super("Canon à Enderpearl", new ItemStack(Material.ENDER_PEARL), 4);
    }

    @Override
    public void equip(BadblockPlayer badblockPlayer) {

    }

    @Override
    public boolean use(BadblockPlayer badblockPlayer, ItemStack item, Action action) {
        if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK){

            EnderPearl enderPearl = badblockPlayer.launchProjectile(EnderPearl.class);
            enderPearl.setVelocity(badblockPlayer.getEyeLocation().getDirection().multiply(1.5f));
            enderPearl.setPassenger(badblockPlayer);

        }
        return true;
    }

    @Override
    public int waitingTime() {
        return 0;
    }
}

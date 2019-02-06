package fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets;

import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class EnderPearlGun extends AbstractGadgets{

    private boolean isActive = false;

    public EnderPearlGun() {
        super("Canon à Enderpearl", new ItemStack(Material.ENDER_PEARL), 4);
    }

    @Override
    public void equip(BadblockPlayer badblockPlayer) {

    }

    @Override
    public void unequip(BadblockPlayer badblockPlayer) {

    }

    @Override
    public boolean use(BadblockPlayer badblockPlayer, ItemStack item, Action action) {
        if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK){

            if(isActive){
                badblockPlayer.sendMessage("Veuillez attendre que le Canon se recharge..");
                return false;
            }

            EnderPearl enderPearl = badblockPlayer.launchProjectile(EnderPearl.class);
            enderPearl.setVelocity(badblockPlayer.getEyeLocation().getDirection().multiply(1.5f));
            enderPearl.setPassenger(badblockPlayer);

            isActive = true;
        }
        return true;
    }

    @Override
    public void handleInteraction(Entity from, Entity to) {
        BadblockPlayer player = (BadblockPlayer) from;

        player.teleport(player.getLocation().add(0D, 1D, 0D));
        isActive = false;
    }

    @Override
    public int waitingTime() {
        return 500;
    }
}

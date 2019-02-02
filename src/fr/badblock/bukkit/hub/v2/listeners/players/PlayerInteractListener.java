package fr.badblock.bukkit.hub.v2.listeners.players;

import fr.badblock.bukkit.hub.v2.games.blockparty.BlockPartyManager;
import fr.badblock.bukkit.hub.v2.games.course.CourseManager;
import fr.badblock.bukkit.hub.v2.games.gladiators.GladiatorManager;
import fr.badblock.bukkit.hub.v2.games.jump.JumpManager;
import fr.badblock.bukkit.hub.v2.games.shoot.ShootManager;
import fr.badblock.bukkit.hub.v2.games.spleef.SpleefManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fr.badblock.bukkit.hub.v2.inventories.InventoriesLoader;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryActionManager;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryActionType;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryObject;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.players.BadblockPlayer;

public class PlayerInteractListener extends BadListener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        BadblockPlayer player = (BadblockPlayer) event.getPlayer();

        // TODO : rewrite this shit.
        if (SpleefManager.getInstance().getSpleefPlayers().containsKey(player)
                || GladiatorManager.getInstance().getCustomInv().containsKey(player)
                || ShootManager.getInstance().getShootPlayers().containsKey(player)
                || BlockPartyManager.getInstance().getBlockPlayers().containsKey(player)
                || CourseManager.getInstance().getWaitingPlayers().contains(player)
                || JumpManager.getInstance().getJumpPlayers().containsKey(player))
        {
            return;
        }

        // default inventory
        ItemStack handItem = player.getInventory().getItemInHand();
        InventoryActionType actionType = InventoryActionType.get(event.getAction());
        if (handItem != null) {
            InventoryObject defaultInventory = InventoriesLoader.getDefaultInventory();
            if (defaultInventory != null) {
                InventoryItemObject itemObject = null;
                for (InventoryItemObject item : defaultInventory.getItems()) {
                    if (player.getInventory().getHeldItemSlot() == item.getPlace()) {
                        itemObject = item;
                        break;
                    }
                }
                if (itemObject != null) {
                    event.setCancelled(!player.hasAdminMode());
                    InventoryActionManager.handle(player, InventoriesLoader.getConfig().getJoinDefaultInventory(), itemObject, actionType);
                }
            }
        }
    }

}

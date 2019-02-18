package fr.badblock.bukkit.hub.v2.listeners.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fr.badblock.api.common.utils.flags.GlobalFlags;
import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets.AbstractGadgets;
import fr.badblock.bukkit.hub.v2.games.blockparty.BlockPartyManager;
import fr.badblock.bukkit.hub.v2.games.course.CourseManager;
import fr.badblock.bukkit.hub.v2.games.gladiators.GladiatorManager;
import fr.badblock.bukkit.hub.v2.games.jump.JumpManager;
import fr.badblock.bukkit.hub.v2.games.shoot.ShootManager;
import fr.badblock.bukkit.hub.v2.games.spleef.SpleefManager;
import fr.badblock.bukkit.hub.v2.inventories.InventoriesLoader;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryActionManager;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryActionType;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryObject;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.players.BadblockPlayer;

public class PlayerInteractListener extends BadListener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent event) {
		BadblockPlayer player = (BadblockPlayer) event.getPlayer();


		if (ConfigLoader.getSwitchers().isGameEnabled())
		{
			if (SpleefManager.getInstance().getSpleefPlayers().containsKey(player)
					|| GladiatorManager.getInstance().getCustomInv().containsKey(player)
					|| ShootManager.getInstance().getShootPlayers().containsKey(player)
					|| BlockPartyManager.getInstance().getBlockPlayers().containsKey(player)
					|| CourseManager.getInstance().getWaitingPlayers().contains(player)
					|| JumpManager.getInstance().getJumpPlayers().containsKey(player)) {
				return;
			}
		}

		HubPlayer hubPlayer = HubPlayer.get(player);

		if (handleWidget(player, hubPlayer, event)) {
			event.setCancelled(true);
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

	private boolean handleWidget(BadblockPlayer player, HubPlayer hubPlayer, PlayerInteractEvent event) {
		AbstractGadgets gadget = hubPlayer.getCurrentWidget();

		if (gadget == null) {
			return false;
		}

		int slot = player.getInventory().getHeldItemSlot();

		if (gadget.getSlot() != slot) {
			return false;
		}

		String flagName = player.getName() + "_gadget_use_" + gadget.getFeatureName();

		if (GlobalFlags.has(flagName)) {
			player.sendTranslatedMessage("features.gadgets_wait");
			return true;
		}

		if (gadget.use(player, player.getItemInHand(), event.getAction()))
			GlobalFlags.set(flagName, gadget.waitingTime());


		return true;
	}

}
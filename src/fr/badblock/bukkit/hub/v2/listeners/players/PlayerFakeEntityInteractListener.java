package fr.badblock.bukkit.hub.v2.listeners.players;

import java.util.Optional;

import org.bukkit.event.EventHandler;

import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryActionType;
import fr.badblock.bukkit.hub.v2.npc.NPC;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.events.PlayerFakeEntityInteractEvent;
import fr.badblock.gameapi.fakeentities.FakeEntity;
import fr.badblock.gameapi.players.BadblockPlayer;

public class PlayerFakeEntityInteractListener extends BadListener
{

	@EventHandler
	public void onPlayerFakeEntityInteract(PlayerFakeEntityInteractEvent event)
	{
		BadblockPlayer player = event.getPlayer();
		FakeEntity<?> fakeEntity = event.getEntity();
		
		if (fakeEntity == null)
		{
			return;
		}
		
		Optional<NPC> optional = NPC.getNpcs().values().stream().filter(npc -> npc.getFakeEntity().equals(fakeEntity)).findFirst();
		
		if (optional == null || !optional.isPresent())
		{
			return;
		}
		
		NPC npc = optional.get();
		
		if (npc.getActions() == null)
		{
			return;
		}
		
		for (InventoryAction action : npc.getActions())
		{
			if (action == null)
			{
				continue;
			}
			
			if (!action.getActionType().equals(InventoryActionType.LEFT_CLICK))
			{
				continue;
			}
			
			if (action.getAction() == null)
			{
				continue;
			}
			
			action.getAction().work(player, action.getAction(), action.getActionData());
		}
	}
	
}
package fr.badblock.bukkit.hub.v2.rabbit;

import fr.badblock.bukkit.hub.v2.instances.hubs.Hub;
import fr.badblock.bukkit.hub.v2.instances.hubs.HubAliveFactory;
import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.technologies.RabbitAPIListener;
import fr.badblock.gameapi.technologies.RabbitListenerType;

public class HubPacketListener  extends RabbitAPIListener
{

	public HubPacketListener()
	{
		super("hub", RabbitListenerType.SUBSCRIBER, false);
	}
	
	@Override
	public void onPacketReceiving(String body)
	{
		if (body == null)
		{
			return;
		}
			
		HubAliveFactory hubAliveFactory = GameAPI.getGson().fromJson(body, HubAliveFactory.class);
		
		if (hubAliveFactory == null)
		{
			return;
		}
		
		Hub hub = Hub.get(hubAliveFactory.getId());
		
		if (hub == null)
		{
			hub = new Hub(hubAliveFactory.getId(), hubAliveFactory.getName());
			hub.create();
		}
		
		hub.keepAlive(hubAliveFactory.getPlayers(), hubAliveFactory.getSlots(), hubAliveFactory.isOpened(),
				hubAliveFactory.getRanks());
	}
	
}

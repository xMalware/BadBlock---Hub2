package fr.badblock.bukkit.hub.v2.rabbit;

import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.Maps;

import fr.badblock.api.common.minecraft.DockerRabbitQueues;
import fr.badblock.api.common.minecraft.InstanceKeepAlive;
import fr.badblock.api.common.utils.TimeUtils;
import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.technologies.RabbitAPIListener;
import fr.badblock.gameapi.technologies.RabbitListenerType;

public class InstanceKeepAliveListener extends RabbitAPIListener
{

	public static ConcurrentMap<String, InstanceKeepAlive> games = Maps.newConcurrentMap();

	private Thread		gameManager;
	
	public InstanceKeepAliveListener()
	{
		super(DockerRabbitQueues.INSTANCE_KEEPALIVE.name(), RabbitListenerType.SUBSCRIBER, false);
		
		gameManager =  new Thread("BadBlockHub/GameManager")
		{
			@Override
			public void run()
			{
				while (BadBlockHub.getInstance().isThreadEnabled())
				{
					Iterator<InstanceKeepAlive> iterator = games.values().iterator();
					
					while (iterator.hasNext())
					{
						InstanceKeepAlive keepAlive = iterator.next();
						
						if (TimeUtils.isExpired(keepAlive.getKeepAliveTime()))
						{
							iterator.remove();
						}
					}
					
					TimeUtils.sleepInSeconds(1);
				}
			}
		};
		
		gameManager.start();
	}

	@Override
	public void onPacketReceiving(String body)
	{
		if (body == null)
		{
			return;
		}
		
		InstanceKeepAlive keepAlive = GameAPI.getGson().fromJson(body, InstanceKeepAlive.class);
		
		if (keepAlive == null)
		{
			return;
		}
		
		keepAlive.keepAlive();
		games.put(keepAlive.getName(), keepAlive);
	}

}
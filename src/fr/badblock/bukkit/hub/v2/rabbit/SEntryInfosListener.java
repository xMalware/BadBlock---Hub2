package fr.badblock.bukkit.hub.v2.rabbit;

import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.Maps;

import fr.badblock.api.common.utils.FullSEntry;
import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.technologies.RabbitAPIListener;
import fr.badblock.gameapi.technologies.RabbitListenerType;

public class SEntryInfosListener extends RabbitAPIListener
{

	public static ConcurrentMap<String, FullSEntry> sentries = Maps.newConcurrentMap();

	public SEntryInfosListener()
	{
		super("networkdocker.sentry.infos", RabbitListenerType.SUBSCRIBER, true);
	}

	@Override
	public void onPacketReceiving(String body)
	{
		if (body == null)
		{
			return;
		}

		FullSEntry sentry = GameAPI.getGson().fromJson(body, FullSEntry.class);

		if (sentry == null)
		{
			return;
		}
		
		sentries.put(sentry.getPrefix(), sentry);
	}

}
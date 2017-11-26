package fr.badblock.bukkit.hub.v2.rabbit;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.docker.factories.GameAliveFactory;
import fr.badblock.gameapi.technologies.RabbitAPIListener;
import fr.badblock.gameapi.technologies.RabbitListenerType;

public class GetServersDataListener extends RabbitAPIListener {

	private static Map<String, GameAliveFactory> data = new HashMap<>();
	
	public GetServersDataListener() {
		super("networkdocker.instance.keepalive", RabbitListenerType.SUBSCRIBER, false);
	}

	@Override
	public void onPacketReceiving(String body) {
		// No data
		if (body == null) return;
		Gson gson = BadBlockHub.getInstance().getNotRestrictiveGson();
		GameAliveFactory gameAliveFactory = gson.fromJson(body, GameAliveFactory.class);
		data.put(gameAliveFactory.getName(), gameAliveFactory);
	}

}

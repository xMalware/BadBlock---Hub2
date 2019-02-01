package fr.badblock.bukkit.hub.v2.utils;

import fr.badblock.api.common.utils.data.Callback;
import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.utils.threading.TaskManager;
import lombok.Data;
import lombok.Getter;

@Data
public class NetworkUtils
{

	@Getter private static NetworkUtils instance = new NetworkUtils();
	
	private int onlinePlayers;
	
	public NetworkUtils()
	{
		fetchOnlinePlayers();
	}
	
	private void fetchOnlinePlayers()
	{
		TaskManager.scheduleSyncRepeatingTask("nbk_updateplayers", new Runnable()
		{
			@Override
			public void run()
			{
				GameAPI.getAPI().getGameServer().getOnlinePlayers(new Callback<Integer>()
				{

					@Override
					public void done(Integer result, Throwable error) {
						onlinePlayers = result;
					}
			
				});
			}
		}, 0, 20 * 10);
	}
	
}

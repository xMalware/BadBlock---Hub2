package fr.badblock.bukkit.hub.v2.utils;

import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.utils.general.Callback;
import fr.badblock.gameapi.utils.threading.TaskManager;
import lombok.Data;
import lombok.Getter;

@Data
@Deprecated /** TODO: new backend **/
public class LadderUtils
{

	@Getter private static LadderUtils instance = new LadderUtils();
	
	private int onlinePlayers;
	
	public LadderUtils()
	{
		fetchOnlinePlayers();
	}
	
	private void fetchOnlinePlayers()
	{
		TaskManager.scheduleSyncRepeatingTask("ladder_updateplayers", new Runnable()
		{
			@Override
			public void run()
			{
				GameAPI.getAPI().getLadderDatabase().sendPing(new String[] { "*" }, new Callback<Integer>()
				{
					@Override
					public void done(Integer arg0, Throwable arg1) {
						setOnlinePlayers(arg0);
					}
				});
			}
		}, 0, 20);
	}
	
}

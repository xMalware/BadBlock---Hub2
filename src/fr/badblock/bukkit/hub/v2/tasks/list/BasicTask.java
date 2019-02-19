package fr.badblock.bukkit.hub.v2.tasks.list;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World;

import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.tasks.HubTask;
import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.utils.BukkitUtils;

public class BasicTask extends HubTask
{

	public BasicTask()
	{
		super (true, 20, 20);
	}

	@Override
	public void run()
	{
		for (World world : Bukkit.getWorlds())
		{
			world.setTime(1200);
		}
		
		BukkitUtils.getAllPlayers().forEach(player -> {
			float nextXp = (float) player.getPlayerData().getXp() + (float) player.getPlayerData().getXpUntilNextLevel();
			float xp = (float) player.getPlayerData().getXp();
			
			float progress = xp / nextXp;
					
			player.setLevel(player.getPlayerData().getLevel());
			player.setExp(progress);
		});
	}

}
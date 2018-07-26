package fr.badblock.bukkit.hub.v2.games.type;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import fr.badblock.bukkit.hub.v2.games.listeners.JumpListener;
import fr.badblock.gameapi.players.BadblockPlayer;

public class Jump extends JumpListener
{
	
	
	BadblockPlayer bp;
	List<Location> checkpoints = new ArrayList<>();
	
	public Jump() 
	{
		bp.removePotionEffects();
		if(bp.getLocation().getBlockY() >= 2)
		{
			bp.teleport((Location) checkpoints);
			Location JumpEnd = new Location(Bukkit.getWorld("world"),-246.470,165,17.576,1.1f,0.8f);
			Location LobSpawn = new Location(Bukkit.getWorld("world"),0.512,82,18.480,0.4f,0.8f);
			if(bp.getLocation().equals(JumpEnd)) 
			{
				if(bp.getLocation().getBlockY() >= 0) 
				{
					bp.teleport(LobSpawn);
					bp.setWalkSpeed((float) 0.4);
				}
			}
		}
	}
	public void Loc() 
	{
		Location Starter = new Location(Bukkit.getWorld("world"),-117.547, 81, 111.444, 1.3f, -1.8f);
		Location CheckPoint1 = new Location(Bukkit.getWorld("world"),-37.526, 108, 172.470, -89.4f, 5.0f);
		Location CheckPoint2 = new Location(Bukkit.getWorld("world"),100.543,94,140.483,-88.4f,-6.4f);
		Location CheckPoint3 = new Location(Bukkit.getWorld("world"),275.460,105,-39.557,179.8f,1.4f);	
		Location CheckPoint4 = new Location(Bukkit.getWorld("world"),231.435,141,-264.502,92f,6.2f);
		Location CheckPoint5 = new Location(Bukkit.getWorld("world"),28.436,123,-339.420,90.2f,5.6f);
		Location CheckPoint6 = new Location(Bukkit.getWorld("world"),12.425,124,-354.465,90.7f,4.2f);
		Location CheckPoint7 = new Location(Bukkit.getWorld("world"),-193.446,109,-191.563,2.1f,-4.8f);
		Location JumpEnd = new Location(Bukkit.getWorld("world"),-246.470,165,17.576,1.1f,0.8f);
		Location LobSpawn = new Location(Bukkit.getWorld("world"),0.512,82,18.480,0.4f,0.8f);
		checkpoints.add(Starter);
		checkpoints.add(CheckPoint1);
		checkpoints.add(CheckPoint2);
		checkpoints.add(CheckPoint3);
		checkpoints.add(CheckPoint4);
		checkpoints.add(CheckPoint5);
		checkpoints.add(CheckPoint6);
		checkpoints.add(CheckPoint7);
		checkpoints.add(JumpEnd);
		checkpoints.add(LobSpawn);
	}
	
}

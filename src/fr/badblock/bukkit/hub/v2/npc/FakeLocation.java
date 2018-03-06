package fr.badblock.bukkit.hub.v2.npc;

import lombok.Data;

@Data
public class FakeLocation
{

	private String	world;
	private int		x;
	private int		y;
	private int		z;
	
	public FakeLocation(String world, int x, int y, int z)
	{
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
}

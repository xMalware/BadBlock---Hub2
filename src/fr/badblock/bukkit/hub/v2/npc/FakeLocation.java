package fr.badblock.bukkit.hub.v2.npc;

import org.bukkit.Location;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import lombok.Data;

@Data
public class FakeLocation
{

	private String	world;
	private int		x;
	private int		y;
	private int		z;
	
	public FakeLocation(Location location)
	{
		this(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
	}
	
	public FakeLocation(String world, int x, int y, int z)
	{
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public DBObject toObject()
	{
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.append("world", this.world);
		dbObject.append("x", this.x);
		dbObject.append("y", this.y);
		dbObject.append("z", this.z);
		return dbObject;
	}
	
	public String toString()
	{
		return "(x = " + this.x + ", y = " + this.y + ", z = " + this.z + ", world = " + this.world + ")";
	}
	
}

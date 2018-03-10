package fr.badblock.bukkit.hub.v2.npc;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import lombok.Data;

@Data
public class FakeLocation
{

	private String	world;
	private double	x;
	private double	y;
	private double	z;
	private float	pitch;
	private float	yaw;
	
	public FakeLocation(Location location)
	{
		this(location.getWorld().getName(), location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getYaw());
	}
	
	public FakeLocation(String world, double x, double y, double z, float pitch, float yaw)
	{
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
	}
	
	public Location toLocation()
	{
		return new Location(Bukkit.getWorld(getWorld()), getX(), getY(), getZ());
	}

	public DBObject toObject()
	{
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.append("world", this.world);
		dbObject.append("x", this.x);
		dbObject.append("y", this.y);
		dbObject.append("z", this.z);
		dbObject.append("pitch", this.pitch);
		dbObject.append("yaw", this.yaw);
		return dbObject;
	}
	
	public String toString()
	{
		return "(x = " + this.x + ", y = " + this.y + ", z = " + this.z + ", pitch = " + this.pitch + ", yaw = " + yaw + ", world = " + this.world + ")";
	}
	
}

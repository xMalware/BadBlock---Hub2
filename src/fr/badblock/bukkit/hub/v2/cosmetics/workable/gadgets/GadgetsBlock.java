package fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class GadgetsBlock
{
	
	Material material;
	byte data;
	
	public GadgetsBlock(Material material, byte data)
	{
		this.material = material;
		this.data = data;
	}
	
	public GadgetsBlock(Material material, int data)
	{
		this(material, (byte) data);
	}
	
	public GadgetsBlock(Material material)
	{
		this(material, (byte) 0);
	}
	
	@SuppressWarnings("deprecation")
	public GadgetsBlock(Block block)
	{
		this(block.getType(), block.getData());
	}
	
	public GadgetsBlock(Location arg0)
	{
		this(arg0.getBlock());
	}
	
	public Material getType()
	{
		return material;
	}
	
	public byte getData()
	{
		return data;
	}

}

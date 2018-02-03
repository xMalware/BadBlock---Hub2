package fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets.type;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets.GadgetsBlock;
import fr.badblock.gameapi.players.BadblockPlayer;
import java.util.*;

public abstract class AbstractGadgets
{
	
    protected final Random RANDOM = new Random();
    protected final BadBlockHub hub;
    protected final BadblockPlayer player;
    protected final Map<Location, GadgetsBlock> blocksUsed;
    protected final Map<Location, GadgetsBlock> blocksBefore;
    protected final List<UUID> interactingPlayers;
    protected Location baseLocation;

    public AbstractGadgets(BadBlockHub hub, BadblockPlayer player)
    {
        this.hub = hub;
        this.player = (BadblockPlayer) player;
        this.blocksUsed = new HashMap<>();
        this.blocksBefore = new HashMap<>();
        this.interactingPlayers = new ArrayList<>();
        this.baseLocation = player.getLocation();
    }

    public abstract void display();
    public abstract void handleInteraction(Entity who, Entity with);
    public abstract boolean isInteractionsEnabled();
    public abstract boolean canUse();

    public void addBlockToUse(Location location, GadgetsBlock block)
    {
        this.blocksUsed.put(new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ()), block);
        this.blocksBefore.put(new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ()), new GadgetsBlock(location.getBlock()));
    }

    public void addBlocksToUse(Map<Location, GadgetsBlock> blocks)
    {
        for (Location block : blocks.keySet())
        {
            this.blocksUsed.put(new Location(block.getWorld(), block.getBlockX(), block.getBlockY(), block.getBlockZ()), blocks.get(block));
            this.blocksBefore.put(new Location(block.getWorld(), block.getBlockX(), block.getBlockY(), block.getBlockZ()), new GadgetsBlock(block.getBlock()));
        }
    }

    public void interactWith(BadblockPlayer player)
    {
        this.interactingPlayers.add(player.getUniqueId());
    }

    @SuppressWarnings("deprecation")
	public void restore()
    {
        for (Location block : this.blocksUsed.keySet())
        {
            block.getBlock().setType(this.blocksBefore.get(block).getType());
            block.getBlock().setData(this.blocksBefore.get(block).getData());
        }

        this.blocksUsed.clear();
        this.blocksBefore.clear();
    }

    @SuppressWarnings("deprecation")
	public void restore(Location location)
    {
        Location finalLocation = new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
        if (this.blocksUsed.containsKey(finalLocation) && this.blocksBefore.containsKey(finalLocation))
        {
            this.blocksUsed.remove(finalLocation);
            finalLocation.getBlock().setType(this.blocksBefore.get(finalLocation).getType());
            finalLocation.getBlock().setData(this.blocksBefore.get(finalLocation).getData());
            this.blocksBefore.remove(finalLocation);
        }
    }

    public BadblockPlayer getPlayer()
    {
        return this.player;
    }

    public Map<Location, GadgetsBlock> getBlocksUsed()
    {
        return this.blocksUsed;
    }

    public boolean isBlockUsed(Location location)
    {
        return this.blocksUsed.containsKey(new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    public boolean isInteractingWith(BadblockPlayer player)
    {
        return this.interactingPlayers.contains(player.getUniqueId());
    }
}

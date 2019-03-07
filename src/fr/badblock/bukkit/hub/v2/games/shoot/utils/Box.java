package fr.badblock.bukkit.hub.v2.games.shoot.utils;

import fr.badblock.gameapi.utils.selections.CuboidSelection;
import lombok.Data;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Toinetoine1 on 18/01/2019.
 */

@Data
public class Box {

    private int blocksLeft = 20;

    private boolean taken;
    private Location particle;
    private CuboidSelection cuboidSelection;
    private List<Location> blocksChanged;

    public Box(Location particle, Location cuboid1, Location cuboid2) {
        this.particle = particle;
        this.cuboidSelection = new CuboidSelection(cuboid1, cuboid2);
        this.taken = false;
        this.blocksChanged = new ArrayList<>();
    }

    @SuppressWarnings("deprecation")
	public void spawnRandomBlocks() {
        Random random = new Random();
        List<Block> blocks = cuboidSelection.getBlocks();

        for (int i = 0; i < blocksLeft; i++) {
            int randomBlocks = random.nextInt(blocks.size());
            DyeColor data = DyeColor.values()[random.nextInt(DyeColor.values().length)];
            Block block = blocks.get(randomBlocks);

            if (blocksChanged.contains(block.getLocation())) {
                i--;
                continue;
            }

            block.setType(Material.WOOL);
            block.setData(data.getWoolData());
            blocksChanged.add(block.getLocation());
        }

    }

    public void restoreBlocks() {
        blocksChanged.forEach(location -> location.getWorld().getBlockAt(location).setType(Material.AIR));
        blocksChanged.clear();
        blocksLeft = 20;
    }

    public int removeBlock(){
        return blocksLeft--;
    }

}

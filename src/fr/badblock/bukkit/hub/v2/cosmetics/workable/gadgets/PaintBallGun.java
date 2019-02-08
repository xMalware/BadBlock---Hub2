package fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.utils.ParticleEffect;
import fr.badblock.gameapi.players.BadblockPlayer;

public class PaintBallGun extends AbstractGadgets {

	private Map<Location, String> blocks = new HashMap<>();
	private ArrayList<Material> blacklist = new ArrayList<>(Arrays.asList(Material.SIGN, Material.SIGN_POST, Material.WALL_SIGN, Material.DOUBLE_PLANT));

	public PaintBallGun(String internalName) {
		super(internalName, new ItemStack(Material.DIAMOND_HOE), 4);
	}

	@Override
	public void equip(BadblockPlayer badblockPlayer) {

	}

	@Override
	public void unequip(BadblockPlayer badblockPlayer) {

	}

	@Override
	public boolean use(BadblockPlayer badblockPlayer, ItemStack item, Action action) {
		if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
			Block block = badblockPlayer.getTargetBlock(new HashSet<Material>(Collections.singletonList(Material.AIR)), 50);
			if (block != null) {

				if (blocks.containsKey(block.getLocation()) || blacklist.contains(block.getType()))
					return false;

				Location start = badblockPlayer.getEyeLocation().clone();
				Location end = block.getLocation();

				Vector vector = end.toVector().subtract(start.toVector());

				for (double i = 1; i <= start.distance(end); i += 0.5) {
					vector.multiply(i);
					start.add(vector);
					ParticleEffect.REDSTONE.display(new ParticleEffect.OrdinaryColor(0, 255, 0), start, 32);
					start.subtract(vector);
					vector.normalize();
				}

				int radius = 1;// 2

				for (int x = -radius; x <= radius; x++) {
					for (int y = -radius; y <= radius; y++) {
						for (int z = -radius; z <= radius; z++) {
							final int X = x;
							final int Y = y;
							final int Z = z;
							Bukkit.getScheduler().runTaskLater(BadBlockHub.getInstance(), new Runnable() 
							{
								
								@Override
								public void run()
								{
									if (!badblockPlayer.isOnline())
									{
										return;
									}
									
									Block b = block.getLocation().clone().add(X, Y, Z).getBlock();
									if(!(b.getType() == Material.AIR) && !blocks.containsKey(b.getLocation()) && !blacklist.contains(b.getType()) && b.getType().isSolid())
									{
										colorBlock(b);
										badblockPlayer.playSound(b.getLocation(), Sound.CHICKEN_EGG_POP);
									}
								}
							}, (Math.abs(x) + Math.abs(y) + Math.abs(z)));
						}
					}
				}


			}
		}

		return true;
	}

	@Override
	public void handleInteraction(Entity from, Entity to) {

	}

	@Override
	public int waitingTime() {
		return 0;
	}

	@SuppressWarnings("deprecation")
	private void colorBlock(Block block) {
		blocks.put(block.getLocation(), block.getTypeId() + ":" + block.getData());

		block.setType(Material.WOOL);
		DyeColor data = DyeColor.values()[new Random().nextInt(DyeColor.values().length)];
		block.setData(data.getWoolData());

		new BukkitRunnable() {
			@Override
			public void run() {
				String[] newBlock = blocks.remove(block.getLocation()).split(":");
				block.setTypeIdAndData(Integer.parseInt(newBlock[0]), Byte.parseByte(newBlock[1]), true);
			}
		}.runTaskLater(BadBlockHub.getInstance(), 40);
	}
}

package fr.badblock.bukkit.hub.v2.games.jump;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JListener implements Listener {

	private Map<String, Integer> jumpvie = new HashMap<>();
	private Map<String, Integer> jumpcheckpoint = new HashMap<>();
	private ArrayList<Player> jumplist = new ArrayList<Player>();
	private int maxLife = 3;
	private Location loc1 = new Location(Bukkit.getWorld("world"), -117, 81, 111);
	private Location loc2 = new Location(Bukkit.getWorld("world"), -37, 108, 172);
	private Location loc3 = new Location(Bukkit.getWorld("world"), 100, 94, 140);
	private Location loc4 = new Location(Bukkit.getWorld("world"), 275, 105, -39);
	private Location loc5 = new Location(Bukkit.getWorld("world"), 231, 141, -264);
	private Location loc6 = new Location(Bukkit.getWorld("world"), 28, 123, -339);
	private Location loc7 = new Location(Bukkit.getWorld("world"), -193, 109, -191);
	// pas de checkpoint car fin public static Location loc8 = new Location(Bukkit.getWorld("world"), -118, 81, 111);


	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if(jumplist.contains(p)) {
			jumplist.remove(p);
			p.sendMessage(JumpManager.JUMP_PREFIX + "§cTu viens de quitter la course");
		}
	}
	
	
	@EventHandler
	public void onPlayerinteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Block b = e.getClickedBlock();
		if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(b != null && (b.getType() == Material.SPRUCE_FENCE_GATE)){
				if(jumplist.contains(p)) {
					jumplist.remove(p);
					p.sendMessage(JumpManager.JUMP_PREFIX + "§cTu viens de quitter le jump");
					jumpcheckpoint.put(p.getName(), 1);
				}
			}
		}
	}

	
	private void checkRespawn(Player p) {
		if(jumpcheckpoint.get(p.getName()) == 1) {
			p.teleport(loc1);
		}else if(jumpcheckpoint.get(p.getName()) == 2) {
			p.teleport(loc2);
		}else if(jumpcheckpoint.get(p.getName()) == 3) {
			p.teleport(loc3);
		}else if(jumpcheckpoint.get(p.getName()) == 4) {
			p.teleport(loc4);
		}else if(jumpcheckpoint.get(p.getName()) == 5) {
			p.teleport(loc5);
		}else if(jumpcheckpoint.get(p.getName()) == 6) {
			p.teleport(loc6);
		}else if(jumpcheckpoint.get(p.getName()) == 7) {
			p.teleport(loc7);	
		}	
		
	}

	  @EventHandler(ignoreCancelled=true, priority= EventPriority.MONITOR)
	  public void onVoidEnter(PlayerMoveEvent e){
		Player p = e.getPlayer();
	    if (e.getTo().getBlockY() < 70) {
	    	if(jumplist.contains(e.getPlayer())) {
	    		jumpvie.put(p.getName(), (jumpvie.get(p.getName()) -1));
	    		if(jumpvie.get(p.getName()) == 0) {
	    			p.sendMessage(JumpManager.JUMP_PREFIX + "§cTu n'as plus de vie! Retente ta chance ;)");
	    			jumplist.remove(p);
	    			jumpvie.remove(p.getName());
	    			jumpcheckpoint.remove(p.getName());
	    			p.performCommand("spawn");
	    		}else if(jumpvie.get(p.getName()) > 0) {
	    			p.sendMessage(JumpManager.JUMP_PREFIX + "§bPouf! Tu viens de respawn au dernier checkpoint. Il te reste §e" + jumpvie.get(p.getName()) + " §bvie(s)");
	    			//p.removePotionEffect(PotionEffectType.ABSORPTION);
	    			//p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2400, jumpvie.get(p.getName())));
	    			checkRespawn(p);
	    		}
	    	}
	    }
	  }
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getAction() == Action.PHYSICAL) {
			if(e.getClickedBlock().getType() == Material.GOLD_PLATE) {
				if(!jumplist.contains(p)) {
					if(e.getClickedBlock().getLocation().equals(new Location(p.getWorld(), -118, 81, 111))) {
						jumplist.add(p);
						jumpvie.put(p.getName(), maxLife);
						jumpcheckpoint.put(p.getName(), 1);
						p.sendMessage(JumpManager.JUMP_PREFIX + "§bTu viens de rejoindre le jump!");
						p.sendMessage(JumpManager.JUMP_PREFIX + "§bTu disposes de §c3 vies");
					}
				}else if(jumplist.contains(p)) {
					if(e.getClickedBlock().getLocation().equals(new Location(p.getWorld(), -38, 108, 172))) {
						jumpcheckpoint.put(p.getName(), 2);
					}else if(e.getClickedBlock().getLocation().equals(new Location(p.getWorld(), 100, 94, 140))) {
						jumpcheckpoint.put(p.getName(), 3);
					}else if(e.getClickedBlock().getLocation().equals(new Location(p.getWorld(), 275, 105, -40))) {
						jumpcheckpoint.put(p.getName(), 4);
					}else if(e.getClickedBlock().getLocation().equals(new Location(p.getWorld(), 231, 141, -265))) {
						jumpcheckpoint.put(p.getName(), 5);
					}else if(e.getClickedBlock().getLocation().equals(new Location(p.getWorld(), 28, 123, -340))) {
						jumpcheckpoint.put(p.getName(), 6);
					}else if(e.getClickedBlock().getLocation().equals(new Location(p.getWorld(), -194, 109, -192))) {;
						jumpcheckpoint.put(p.getName(), 7);
					}else if(e.getClickedBlock().getLocation().equals(new Location(p.getWorld(), -247, 165, 17))) {
						Bukkit.broadcastMessage("§bFélicitation à §c" + p.getName() + "§b qui a réussi à terminer le jump!");
						p.performCommand("spawn");
						jumpcheckpoint.remove(p.getName());
						jumplist.remove(p);
						jumpvie.remove(p.getName());
					}
				}
			}
		}
		
	}
	
}




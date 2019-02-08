package fr.badblock.bukkit.hub.v2.games.shoot.events;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.games.shoot.ShootManager;
import fr.badblock.bukkit.hub.v2.games.shoot.utils.Box;
import fr.badblock.bukkit.hub.v2.games.shoot.utils.ShootPlayer;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.lang.reflect.Field;

/**
 * Created by Toinetoine1 on 19/01/2019.
 */
public class ShootBlock implements Listener {

    @EventHandler
    private void onProjectileHit(final ProjectileHitEvent e) {
        if (e.getEntityType() == EntityType.ARROW) {

            Bukkit.getScheduler().scheduleSyncDelayedTask(BadBlockHub.getInstance(), () -> {
                try {

                    net.minecraft.server.v1_8_R3.EntityArrow entityArrow = ((CraftArrow) e
                            .getEntity()).getHandle();

                    Field fieldX = net.minecraft.server.v1_8_R3.EntityArrow.class
                            .getDeclaredField("d");
                    Field fieldY = net.minecraft.server.v1_8_R3.EntityArrow.class
                            .getDeclaredField("e");
                    Field fieldZ = net.minecraft.server.v1_8_R3.EntityArrow.class
                            .getDeclaredField("f");

                    fieldX.setAccessible(true);
                    fieldY.setAccessible(true);
                    fieldZ.setAccessible(true);

                    int x = fieldX.getInt(entityArrow);
                    int y = fieldY.getInt(entityArrow);
                    int z = fieldZ.getInt(entityArrow);

                    if (isValidBlock(y)) {
                        Block block = e.getEntity().getWorld().getBlockAt(x, y, z);
                        if(e.getEntity().getShooter() instanceof Player){
                            Player player = (Player) e.getEntity().getShooter();
                            if(GameState.INGAME.equals(ShootManager.getInstance().getGameState()) && ShootManager.getInstance().getShootPlayers().containsKey(player)) {
                                ShootPlayer shootPlayer = ShootManager.getInstance().getShootPlayers().get(player);
                                if(shootPlayer.getBox().getBlocksChanged().contains(block.getLocation())){
                                    block.setType(Material.AIR);
                                    player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 2, 1);
                                    e.getEntity().remove();
                                    player.sendMessage(shootPlayer.getBox().getBlocksLeft() == 1 ? ShootManager.SHOOT_PREFIX+"§cVous gagnez la partie !"
                                            : ShootManager.SHOOT_PREFIX+"§cBlocs à shooter : §3"+(shootPlayer.getBox().getBlocksLeft() - 1));

                                    if(shootPlayer.getBox().removeBlock() <= 1){
                                        ShootManager.getInstance().getShootPlayers().forEach((p, shootPlayer1) -> {
                                                p.sendMessage(ShootManager.SHOOT_PREFIX + "§cLe joueur " + player.getName() + " gagne le tir à l'arc !");
                                            p.playSound(p.getLocation(), Sound.LEVEL_UP, 2, 1);
                                            shootPlayer1.getCustomPlayerInventory().restoreInventory(p);
                                            p.performCommand("spawn");
                                        });

                                        ShootManager.getInstance().getBoxes().forEach(box -> box.setTaken(false));

                                        ShootManager.getInstance().getShootPlayers().clear();
                                        ShootManager.getInstance().getBoxes().forEach(Box::restoreBlocks);
                                        ShootManager.getInstance().setGameState(GameState.WAITING);
                                    }
                                }
                            }
                        }
                    }

                } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException | SecurityException e1) {
                    e1.printStackTrace();
                }
            });

        }
    }

    private boolean isValidBlock(int y) {
        return y != -1;
    }


}

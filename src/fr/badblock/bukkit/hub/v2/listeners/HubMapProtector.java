package fr.badblock.bukkit.hub.v2.listeners;

import fr.badblock.bukkit.hub.v2.games.gladiators.GladiatorManager;
import fr.badblock.bukkit.hub.v2.games.shoot.ShootManager;
import fr.badblock.bukkit.hub.v2.games.spleef.SpleefManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.servers.MapProtector;

public class HubMapProtector implements MapProtector {

    @Override
    public boolean allowBlockFormChange(Block block) {
        return true;
    }

    @Override
    public boolean allowBlockPhysics(Block block) {
        return true;
    }

    @Override
    public boolean allowExplosion(Location location) {
        return false;
    }

    @Override
    public boolean allowFire(Block block) {
        return false;
    }

    @Override
    public boolean allowInteract(Entity entity) {
        return false;
    }

    @Override
    public boolean allowLeavesDecay(Block block) {
        return false;
    }

    @Override
    public boolean allowMelting(Block block) {
        return false;
    }

    @Override
    public boolean allowPistonMove(Block block) {
        return false;
    }

    @Override
    public boolean allowRaining() {
        return false;
    }

    @Override
    public boolean blockBreak(BadblockPlayer player, Block block) {
        if (SpleefManager.getInstance().getSpleefPlayers().containsKey(player) && block != null) {
            return block.getType().equals(Material.SNOW_BLOCK) && !SpleefManager.getInstance().getSpleefPlayers().get(player).isDead();
        }

        return player.hasAdminMode();
    }

    @Override
    public boolean blockPlace(BadblockPlayer player, Block block) {
        return player.hasAdminMode();
    }

    @Override
    public boolean canBeingDamaged(BadblockPlayer player) {
        if (player != null) {
            return GladiatorManager.getInstance().getCustomInv().containsKey(player);
        }
        return false;
    }

    @Override
    public boolean canBlockDamage(Block block) {
        return true;
    }

    @Override
    public boolean canCombust(Entity entity) {
        return false;
    }

    @Override
    public boolean canCreatureSpawn(Entity creature, boolean isPlugin) {
        return isPlugin;
    }

    @Override
    public boolean canDrop(BadblockPlayer player) {
        return player.hasAdminMode();
    }

    @Override
    public boolean canEmptyBucket(BadblockPlayer player) {
        return player.hasAdminMode();
    }

    @Override
    public boolean canEnchant(BadblockPlayer player, Block table) {
        return player.hasAdminMode();
    }

    @Override
    public boolean canEntityBeingDamaged(Entity entity) {
        return false;
    }

    @Override
    public boolean canFillBucket(BadblockPlayer player) {
        return player.hasAdminMode();
    }

    @Override
    public boolean canInteract(BadblockPlayer player, Action action, Block block) {
        if (SpleefManager.getInstance().getSpleefPlayers().containsKey(player) && block != null) {
            return (action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.LEFT_CLICK_BLOCK)) && block.getType().equals(Material.SNOW_BLOCK);
        } else if (ShootManager.getInstance().getShootPlayers().containsKey(player)) {
            return player.getItemInHand().getType() == Material.BOW;
        } else if(GladiatorManager.getInstance().getCustomInv().containsKey(player)){
            return true;
        }

        return player.hasAdminMode();
    }

    @Override
    public boolean canInteractArmorStand(BadblockPlayer player, ArmorStand entity) {
        return player.hasAdminMode();
    }

    @Override
    public boolean canInteractEntity(BadblockPlayer player, Entity entity) {
        return player.hasAdminMode();
    }

    @Override
    public boolean canItemDespawn(Item item) {
        return true;
    }

    @Override
    public boolean canItemSpawn(Item item) {
        return true;
    }

    @Override
    public boolean canLostFood(BadblockPlayer player) {
        return false;
    }

    @Override
    public boolean canPickup(BadblockPlayer player) {
        return player.hasAdminMode();
    }

    @Override
    public boolean canSoilChange(Block soil) {
        return false;
    }

    @Override
    public boolean canSpawn(Entity entity) {
        return true;
    }

    @Override
    public boolean canUseBed(BadblockPlayer player, Block bed) {
        return player.hasAdminMode();
    }

    @Override
    public boolean canUsePortal(BadblockPlayer player) {
        return player.hasAdminMode();
    }

    @Override
    public boolean destroyArrow() {
        return true;
    }

    @Override
    public boolean healOnJoin(BadblockPlayer player) {
        return false;
    }

    @Override
    public boolean modifyItemFrame(BadblockPlayer player, Entity itemFrame) {
        return player.hasAdminMode();
    }

    @Override
    public boolean modifyItemFrame(Entity itemframe) {
        return false;
    }

    @Override
    public boolean canEntityBeingDamaged(Entity entity, BadblockPlayer badblockPlayer) {
        return false;
    }

}

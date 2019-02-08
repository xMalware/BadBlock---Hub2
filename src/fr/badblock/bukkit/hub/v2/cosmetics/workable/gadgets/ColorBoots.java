package fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.badblock.bukkit.hub.v2.games.utils.ItemBuilder;
import fr.badblock.gameapi.players.BadblockPlayer;

public class ColorBoots extends AbstractGadgets {

    public ColorBoots(String internalName) {
        super(internalName, new ItemStack(Material.DIAMOND_BOOTS), 4);
    }

    @Override
    public void equip(BadblockPlayer badblockPlayer) {
        ItemStack customBoots = new ItemBuilder(getItem()).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).toItemStack();
        badblockPlayer.getInventory().setBoots(customBoots);
    }

    @Override
    public void unequip(BadblockPlayer badblockPlayer) {
        badblockPlayer.getInventory().setBoots(null);
    }

    @Override
    public boolean use(BadblockPlayer badblockPlayer, ItemStack item, Action action) {
        badblockPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 6));
        return true;
    }

    @Override
    public void handleInteraction(Entity from, Entity to) {

    }

    @Override
    public int waitingTime() {
        return 22000;
    }
}

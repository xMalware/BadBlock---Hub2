package fr.badblock.bukkit.hub.v2.games.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Toinetoine1 on 17/01/2019.
 */
public class CustomPlayerInventory {

    Map<UUID, ItemStack[]> items = new HashMap<>();
    Map<UUID, ItemStack[]> armor = new HashMap<>();

    public void storeAndClearInventory(Player player) {
        UUID uuid = player.getUniqueId();

        ItemStack[] contents = player.getInventory().getContents();
        ItemStack[] armorContents = player.getInventory().getArmorContents();

        items.put(uuid, contents);
        armor.put(uuid, armorContents);

        player.getInventory().clear();

        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        player.updateInventory();
    }

    public void restoreInventory(Player player) {
        UUID uuid = player.getUniqueId();

        ItemStack[] contents = items.get(uuid);
        ItemStack[] armorContents = armor.get(uuid);

        if (contents != null) {
            player.getInventory().setContents(contents);
        } else {
            player.getInventory().clear();
        }

        if (armorContents != null) {
            player.getInventory().setArmorContents(armorContents);
        } else {
            player.getInventory().setHelmet(null);
            player.getInventory().setChestplate(null);
            player.getInventory().setLeggings(null);
            player.getInventory().setBoots(null);
        }
        player.updateInventory();
    }

}

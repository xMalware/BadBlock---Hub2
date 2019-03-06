package fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.utils.FNPC;
import fr.badblock.gameapi.players.BadblockPlayer;

public class CloneNPC extends AbstractGadgets{

    private FNPC npc;

    public CloneNPC(String internalName) {
        super(internalName, new ItemStack(Material.SKULL_ITEM), 4);
    }

    @Override
    public void equip(BadblockPlayer badblockPlayer) {

    }

    @Override
    public void unequip(BadblockPlayer badblockPlayer) {

    }

    @Override
    public boolean use(BadblockPlayer badblockPlayer, ItemStack item, Action action) {
        GameProfile gameProfile = ((CraftPlayer) badblockPlayer).getProfile();
        Property property = gameProfile.getProperties().get("textures").iterator().next();

        String texture = property.getValue();
        String signature = property.getSignature();

        npc = new FNPC(texture, signature, badblockPlayer.getLocation(), null);
        npc.spawn("§c§l" + badblockPlayer.getName());

        new BukkitRunnable(){

            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> npc.despawn());
            }
        }.runTaskLater(BadBlockHub.getInstance(), 100);

        return true;
    }

    @Override
    public void handleInteraction(Entity from, Entity to) {

    }

    @Override
    public int waitingTime() {
        return 30000;
    }
}

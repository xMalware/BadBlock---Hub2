package fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets;

import fr.badblock.bukkit.hub.v2.games.utils.ItemBuilder;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Data;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

@Data
public abstract class AbstractGadgets {

    private String name;
    private ItemStack item;
    private int slot;

    public AbstractGadgets(String gadgetsName, ItemStack item, int slot) {
        this.name = gadgetsName;
        this.item = new ItemBuilder(item).setName("§c"+gadgetsName).toItemStack();
        this.slot = slot;
    }

    public abstract void equip(BadblockPlayer badblockPlayer);
    public abstract boolean use(BadblockPlayer badblockPlayer, ItemStack item, Action action);
    public abstract int waitingTime();

    public ItemStack getItem() {
        return item;
    }
}

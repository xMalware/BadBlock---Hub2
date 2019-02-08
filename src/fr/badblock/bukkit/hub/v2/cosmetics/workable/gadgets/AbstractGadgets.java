package fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets;

import org.bukkit.entity.Entity;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.games.utils.ItemBuilder;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Data;

@Data
public abstract class AbstractGadgets {

    private String featureName;
    private ItemStack item;
    private int slot;
    private boolean external;

    public AbstractGadgets(String internalName, ItemStack item, int slot)
    {
        this.featureName = ConfigLoader.getFeatures().getFeatures().get("gadget_" + internalName.toLowerCase()).getName();
        this.item = new ItemBuilder(item).setName(featureName).toItemStack();

        this.slot = slot;
        this.external = false;
    }

    public AbstractGadgets(String gadgetsName, ItemStack item, int slot, boolean isExternal)
    {
        this.featureName = ConfigLoader.getFeatures().getFeatures().get("gadget_" + gadgetsName.toLowerCase()).getName();
        
        if (item != null)
            this.item = new ItemBuilder(item).setName(featureName).toItemStack();
        this.slot = slot;
        this.external = isExternal;
    }

    public abstract void equip(BadblockPlayer badblockPlayer);

    public abstract void unequip(BadblockPlayer badblockPlayer);

    public abstract boolean use(BadblockPlayer badblockPlayer, ItemStack item, Action action);

    public abstract void handleInteraction(Entity from, Entity to);

    public abstract int waitingTime();

    public ItemStack getItem() {
        return item;
    }
}

package fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets;

import com.yapzhenyie.GadgetsMenu.api.GadgetsMenuAPI;
import com.yapzhenyie.GadgetsMenu.cosmetics.gadgets.GadgetType;
import com.yapzhenyie.GadgetsMenu.player.PlayerManager;
import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class ExternalGadgets extends AbstractGadgets{

    private GadgetType gadgetType;
    public boolean isActive;

    public ExternalGadgets(String internalName, ItemStack item, GadgetType gadgetType) {
        super(internalName, item, 4, true);

        this.gadgetType = gadgetType;
        this.isActive = false;
    }

    @Override
    public void equip(BadblockPlayer badblockPlayer) {
        PlayerManager playerManager = GadgetsMenuAPI.getPlayerManager(badblockPlayer);

        playerManager.equipGadget(gadgetType);
    }

    @Override
    public void unequip(BadblockPlayer badblockPlayer) {

    }

    @Override
    public boolean use(BadblockPlayer badblockPlayer, ItemStack item, Action action) {
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

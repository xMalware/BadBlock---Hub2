package fr.badblock.bukkit.hub.v2.inventories.actions;

import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.bukkit.hub.v2.utils.DisguiseUtil;
import fr.badblock.gameapi.players.BadblockPlayer;

public class ItemActionRemoveDisguise extends CustomItemAction {

    @Override
    public void execute(BadblockPlayer player, CustomItemActionType action, String actionData) {
        DisguiseUtil disguiseUtil = DisguiseUtil.instanceOf(player);

        if (disguiseUtil != null) {
            disguiseUtil.removeDisguise();
            player.sendMessage("§cMétamorphose retirée");
        } else {
            player.sendMessage("§cAucune métamorphose active");
        }
    }

}

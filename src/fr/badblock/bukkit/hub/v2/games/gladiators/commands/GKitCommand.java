package fr.badblock.bukkit.hub.v2.games.gladiators.commands;

import fr.badblock.bukkit.hub.v2.games.gladiators.kits.KitsManager;
import fr.badblock.bukkit.hub.v2.games.gladiators.maps.Map;
import fr.badblock.bukkit.hub.v2.games.gladiators.maps.MapManager;
import fr.badblock.bukkit.hub.v2.games.utils.CustomPlayerInventory;
import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class GKitCommand implements CommandExecutor {

    private HashMap<Player, CustomPlayerInventory> inventories = new HashMap<>();
    private HashMap<Player, String> kitName = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if (sender instanceof Player) {
            BadblockPlayer player = (BadblockPlayer) sender;
            if (player.hasPermission("badevent.admin")) {
                if(args.length == 1){
                    if(args[0].equalsIgnoreCase("save")){
                        if(inventories.containsKey(player)){
                            KitsManager.saveKit(player.getInventory(), kitName.get(player));

                            inventories.remove(player).restoreInventory(player);
                            kitName.remove(player);

                            player.sendMessage("Kit sauvegardé !");
                        }

                    }
                } else if (args.length == 2) {
                    if(args[0].equalsIgnoreCase("edit")){
                        if (!inventories.containsKey(player) && MapManager.get().getMaps().stream().anyMatch(map -> map.getName().equals(args[1]))) {
                            Map map = MapManager.get().getMap(args[1]);

                            CustomPlayerInventory customPlayerInventory = new CustomPlayerInventory();
                            customPlayerInventory.storeAndClearInventory(player);
                            inventories.put(player, customPlayerInventory);
                            kitName.put(player, map.getName());

                            if(!map.giveKit(player)){
                                player.getInventory().clear();
                            }
                            player.sendMessage("§cEditez le kit puis taper /gkit save");
                        }
                    }

                }
            }

        }

        return false;
    }
}

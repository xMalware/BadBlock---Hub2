package fr.badblock.bukkit.hub.v2.games.gladiators.commands;

import fr.badblock.bukkit.hub.v2.games.gladiators.GladiatorManager;
import fr.badblock.bukkit.hub.v2.games.gladiators.maps.Map;
import fr.badblock.bukkit.hub.v2.games.gladiators.maps.MapManager;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GQuitCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player)sender;

            if(MapManager.get().getMaps().stream().anyMatch(map -> map.getPlayers().contains(player))){
                Map map = MapManager.get().getMaps().stream().filter(map1 -> map1.getPlayers().contains(player)).findAny().get();

                GladiatorManager.getInstance().getCustomInv().remove(player).restoreInventory(player);
                map.getPlayers().remove(player);
                player.performCommand("spawn");
                player.sendMessage(GladiatorManager.GLADIATOR_PREFIX+"§cVous avez quitté le Gladiator !");

                for(Sign sign1 : map.getSignLocations()){
                    sign1.setLine(3, "§8"+map.getPlayers().size());
                    sign1.update();
                }
            }

        }


        return false;
    }
}

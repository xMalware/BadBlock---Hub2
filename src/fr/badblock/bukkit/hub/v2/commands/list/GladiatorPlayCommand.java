package fr.badblock.bukkit.hub.v2.commands.list;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.badblock.bukkit.hub.v2.games.gladiators.GladiatorManager;
import fr.badblock.bukkit.hub.v2.games.gladiators.events.GladiatorInteract;
import fr.badblock.bukkit.hub.v2.games.gladiators.maps.Map;
import fr.badblock.bukkit.hub.v2.games.gladiators.maps.MapManager;
import fr.badblock.gameapi.command.AbstractCommand;
import fr.badblock.gameapi.players.BadblockPlayer;

public class GladiatorPlayCommand extends AbstractCommand {


    public GladiatorPlayCommand() {
        super("playgladiator", null, BadblockPlayer.GamePermission.PLAYER);
        this.allowConsole(false);
    }

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            if(args.length == 1){
                if(MapManager.get().getMaps().stream().anyMatch(map -> map.getName().equalsIgnoreCase(args[0]))){
                    Map map = MapManager.get().getMap(args[0]);

                    if(!map.giveKit(player)){
                        player.sendMessage(GladiatorManager.GLADIATOR_PREFIX+"Le kit n'a pas été configuré !");
                        return true;
                    }

                    GladiatorInteract.playGladiator(player, map);
                }

            }
        }
        return false;
    }
}

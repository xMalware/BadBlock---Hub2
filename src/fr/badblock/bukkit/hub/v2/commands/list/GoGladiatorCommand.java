package fr.badblock.bukkit.hub.v2.commands.list;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.games.gladiators.GladiatorManager;
import fr.badblock.bukkit.hub.v2.utils.FeatureUtils;
import fr.badblock.gameapi.command.AbstractCommand;
import fr.badblock.gameapi.players.BadblockPlayer;

public class GoGladiatorCommand extends AbstractCommand {


    public GoGladiatorCommand() {
        super("gogladiator", null, BadblockPlayer.GamePermission.PLAYER);
        this.allowConsole(false);
    }

    @Override
    public boolean executeCommand(CommandSender commandSender, String[] strings) {
    	if (!ConfigLoader.getSwitchers().isGameEnabled())
    	{
    		return true;
    	}
    	
        if(commandSender instanceof Player) {
            BadblockPlayer p = (BadblockPlayer) commandSender;

            if(FeatureUtils.isInAGame(p)){
                p.sendMessage("§cTu es dejà dans en partie.");
                return true;
            }

            p.teleport(GladiatorManager.getInstance().getTeleportPoint());
        }

        return false;
    }
}

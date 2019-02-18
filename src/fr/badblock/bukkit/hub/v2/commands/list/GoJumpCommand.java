package fr.badblock.bukkit.hub.v2.commands.list;

import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.games.jump.JumpManager;
import fr.badblock.gameapi.command.AbstractCommand;
import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GoJumpCommand extends AbstractCommand {


    public GoJumpCommand() {
        super("gojump", null, BadblockPlayer.GamePermission.PLAYER);
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

            p.teleport(JumpManager.getInstance().getTeleportPoint());
        }

        return false;
    }
}

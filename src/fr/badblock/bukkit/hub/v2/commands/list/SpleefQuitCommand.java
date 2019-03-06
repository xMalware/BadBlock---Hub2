package fr.badblock.bukkit.hub.v2.commands.list;

import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.games.spleef.SpleefManager;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import fr.badblock.gameapi.command.AbstractCommand;
import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpleefQuitCommand extends AbstractCommand {


    public SpleefQuitCommand() {
        super("quitspleef", null, BadblockPlayer.GamePermission.PLAYER);
        this.allowConsole(false);
    }

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
    	if (!ConfigLoader.getSwitchers().isGameEnabled())
    	{
    		return true;
    	}
    	
        if(sender instanceof Player) {
            Player p = (Player) sender;

            if(GameState.WAITING.equals(SpleefManager.getInstance().getGameState()) && SpleefManager.getInstance().getSpleefPlayers().containsKey(p)){
                SpleefManager.getInstance().getSpleefPlayers().remove(p).getCustomInv().restoreInventory(p);
                p.teleport(SpleefManager.getInstance().getTeleportPoint());
                p.sendMessage("§cVous avez quitté le Spleef !");
            }

        }


        return false;
    }
}

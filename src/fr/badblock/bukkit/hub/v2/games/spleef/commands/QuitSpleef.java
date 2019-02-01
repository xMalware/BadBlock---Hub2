package fr.badblock.bukkit.hub.v2.games.spleef.commands;

import fr.badblock.bukkit.hub.v2.games.spleef.SpleefManager;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Toinetoine1 on 21/01/2019.
 */

public class QuitSpleef implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;

            if(SpleefManager.getInstance().getGameState().isState(GameState.WAITING) && SpleefManager.getInstance().getSpleefPlayers().containsKey(p)){
                SpleefManager.getInstance().getSpleefPlayers().remove(p).getCustomInv().restoreInventory(p);
                p.performCommand("spawn");
                p.sendMessage("§cVous avez quitté le Spleef !");
            }

        }


        return false;
    }

}

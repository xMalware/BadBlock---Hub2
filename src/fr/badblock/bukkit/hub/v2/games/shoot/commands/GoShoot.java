package fr.badblock.bukkit.hub.v2.games.shoot.commands;

import fr.badblock.bukkit.hub.v2.games.course.CourseManager;
import fr.badblock.bukkit.hub.v2.games.jump.JumpManager;
import fr.badblock.bukkit.hub.v2.games.shoot.ShootManager;
import fr.badblock.bukkit.hub.v2.games.spleef.SpleefManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Toinetoine1 on 19/01/2019.
 */
public class GoShoot implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;

            if(CourseManager.getInstance().getWaitingPlayers().contains(p) || JumpManager.getInstance().getJumpPlayers().containsKey(p) ||
                    SpleefManager.getInstance().getSpleefPlayers().containsKey(p) || ShootManager.getInstance().getShootPlayers().containsKey(p)){
                p.sendMessage("§cTu es dejà une Partie !");
                return true;
            }

            p.teleport(ShootManager.getInstance().getShootLoc());
        }
        return false;
    }

}

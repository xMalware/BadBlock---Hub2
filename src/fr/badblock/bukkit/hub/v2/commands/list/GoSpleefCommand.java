package fr.badblock.bukkit.hub.v2.commands.list;

import fr.badblock.bukkit.hub.v2.games.course.CourseManager;
import fr.badblock.bukkit.hub.v2.games.jump.JumpManager;
import fr.badblock.bukkit.hub.v2.games.shoot.ShootManager;
import fr.badblock.bukkit.hub.v2.games.spleef.SpleefManager;
import fr.badblock.bukkit.hub.v2.utils.FeatureUtils;
import fr.badblock.gameapi.command.AbstractCommand;
import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GoSpleefCommand extends AbstractCommand {


    public GoSpleefCommand() {
        super("gospleef", null, BadblockPlayer.GamePermission.PLAYER);
        this.allowConsole(false);
    }

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            BadblockPlayer p = (BadblockPlayer) sender;

            if(FeatureUtils.isInAGame(p)){
                p.sendMessage("§cTu es dejà dans une partie.");
                return true;
            }

            p.teleport(SpleefManager.getInstance().getTeleportPoint());
        }
        return false;
    }
}

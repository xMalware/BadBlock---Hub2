package fr.badblock.bukkit.hub.v2.utils;

import com.yapzhenyie.GadgetsMenu.api.GadgetsMenuAPI;
import com.yapzhenyie.GadgetsMenu.player.PlayerManager;

import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.games.blockparty.BlockPartyManager;
import fr.badblock.bukkit.hub.v2.games.course.CourseManager;
import fr.badblock.bukkit.hub.v2.games.gladiators.GladiatorManager;
import fr.badblock.bukkit.hub.v2.games.jump.JumpManager;
import fr.badblock.bukkit.hub.v2.games.shoot.ShootManager;
import fr.badblock.bukkit.hub.v2.games.spleef.SpleefManager;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;

public class FeatureUtils {

    public static void removeAllFeatures(BadblockPlayer player) {
        HubPlayer hubPlayer = HubPlayer.get(player);

        if (hubPlayer != null) {

            //REMOVE PET
            if (!(hubPlayer.getPet() == null)) {
                hubPlayer.getPet().undeploy(player);
                hubPlayer.setPet(null);
            }

            //REMOVE Particle
            if (!(hubPlayer.getEffect() == null)) {
                hubPlayer.getEffect().cancel();
                hubPlayer.setEffect(null);
            }

            //REMOVE Disguise
            DisguiseUtil disguiseUtil = DisguiseUtil.instanceOf(player);
            if (disguiseUtil != null) {
                disguiseUtil.removeDisguise();
            }

            //REMOVE Gadgets
            PlayerManager playerManager = GadgetsMenuAPI.getPlayerManager(player);

            if (playerManager != null)
                playerManager.removeGadget();

            //REMOVE Mount
            if (hubPlayer.getMountEntity() != null && hubPlayer.getMountEntity().isValid())
                hubPlayer.getMountEntity().remove();
        }

    }

    public static boolean isInAGame(BadblockPlayer badblockPlayer){
		if (!ConfigLoader.getSwitchers().isGameEnabled())
		{
			return false;
		}
		
        return BlockPartyManager.getInstance().getBlockPlayers().containsKey(badblockPlayer)
                || CourseManager.getInstance().getWaitingPlayers().contains(badblockPlayer)
                || GladiatorManager.getInstance().getCustomInv().containsKey(badblockPlayer)
                || JumpManager.getInstance().getJumpPlayers().containsKey(badblockPlayer)
                || ShootManager.getInstance().getShootPlayers().containsKey(badblockPlayer)
                || SpleefManager.getInstance().getSpleefPlayers().containsKey(badblockPlayer);
    }

}

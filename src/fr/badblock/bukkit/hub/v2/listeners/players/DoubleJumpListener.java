package fr.badblock.bukkit.hub.v2.listeners.players;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import fr.badblock.bukkit.hub.v2.games.course.CourseManager;
import fr.badblock.bukkit.hub.v2.utils.ParticleEffect;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DoubleJumpListener extends BadListener {

    private final List<UUID> allowed;

    public DoubleJumpListener() {
        this.allowed = new ArrayList<>();
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        BadblockPlayer player = (BadblockPlayer) event.getPlayer();

        if (player.getGameMode() != GameMode.ADVENTURE)
            return;

        if (player.getAllowFlight())
            return;

        if (CourseManager.getInstance().getWaitingPlayers().contains(player))
            return;

        if (((LivingEntity) player).isOnGround()) {
            player.setAllowFlight(true);
            this.allowed.add(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        BadblockPlayer player = (BadblockPlayer) event.getPlayer();

        if (!this.allowed.contains(player.getUniqueId()))
            return;

        this.allowed.remove(player.getUniqueId());

        event.setCancelled(true);

        player.setVelocity(player.getLocation().getDirection().multiply(1.6D).setY(1.0D));
        player.playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS, 1.0F, 1.0F);

        for (int i = 0; i < 20; i++)
            ParticleEffect.CLOUD.display(0.5F, 0.15F, 0.5F, 0.25F, 20, player.getLocation().subtract(0.0F, 0.20F, 0.0F), 16);

        player.setAllowFlight(false);
    }


}

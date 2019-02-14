package fr.badblock.bukkit.hub.v2.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.players.HubStoredPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;

public class ParticleListener {

    public static void registerParticleListener() {
        final ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        manager.addPacketListener(
                new PacketAdapter(BadBlockHub.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Server.WORLD_PARTICLES) {

                    @Override
                    public void onPacketSending(PacketEvent event) {
                        BadblockPlayer badblockPlayer = (BadblockPlayer) event.getPlayer();

                        if (HubStoredPlayer.get(badblockPlayer).isHidePlayers()) {
                            event.setCancelled(true);
                        }

                    }
                }
        );
    }

}

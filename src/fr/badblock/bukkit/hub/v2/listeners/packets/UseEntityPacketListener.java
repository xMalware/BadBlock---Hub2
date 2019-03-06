package fr.badblock.bukkit.hub.v2.listeners.packets;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.utils.FNPC;
import fr.badblock.gameapi.players.BadblockPlayer;

public class UseEntityPacketListener
{

	public UseEntityPacketListener() {
		List<PacketType> packets = new ArrayList<>();
		// Adding the packets
		packets.add(PacketType.Play.Client.USE_ENTITY);
		ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
		packets.forEach(packet -> {
			protocolManager.addPacketListener(
					new PacketAdapter(BadBlockHub.getInstance(), ListenerPriority.LOWEST, packet) {
						@Override
						public void onPacketReceiving(PacketEvent event) {
							Player player = event.getPlayer();
							
							if (!(player instanceof BadblockPlayer))
							{
								return;
							}
							
                            PacketContainer packet = event.getPacket();
							
							try
							{	
	                            int entityId = packet.getIntegers().read(0);
	                            
	                            if (FNPC.npcs.containsKey(entityId))
	                            {
	    							BadblockPlayer p = (BadblockPlayer) player;
	    							
	                            	FNPC fnpc = FNPC.npcs.get(entityId);
	                            	if (fnpc.n != null)
	                            	{
	                            		fnpc.n.getActions().get(0).getAction().work(p, fnpc.n.getActions().get(0).getAction(), fnpc.n.getActions().get(0).getActionData());
	                            	}
	                            }
							}
							catch (Exception error)
							{
								error.printStackTrace();
							}
						}
					});
		});
	}

}
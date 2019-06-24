package fr.badblock.bukkit.hub.v2.players;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Friend
{

	private String name;
	private boolean accepted;
	private boolean online;
	private String	server;
	
}

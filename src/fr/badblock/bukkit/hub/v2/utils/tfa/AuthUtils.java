package fr.badblock.bukkit.hub.v2.utils.tfa;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;

import com.warrenstrange.googleauth.GoogleAuthenticator;

import fr.badblock.gameapi.players.BadblockPlayer;

public class AuthUtils
{

	public static GoogleAuthenticator gAuth 			= new GoogleAuthenticator();
	public static Map<String, String> tempPlayersKeys   = new HashMap<>();
	
	// Same code in BadAuthenticate
	public static String getAuthKey(String player)
	{
		BadblockPlayer pl = (BadblockPlayer) Bukkit.getPlayer(player);
		
		if (pl.getObject() == null || !pl.getObject().has("authKey"))
		{
			return null;
		}
		
		return pl.getObject().get("authKey").getAsString();
	}
	
}

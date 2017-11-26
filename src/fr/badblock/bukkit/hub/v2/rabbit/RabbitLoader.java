package fr.badblock.bukkit.hub.v2.rabbit;

public class RabbitLoader {

	/**
	 * Load rabbit listeners
	 */
	public static void load() {
		try {
			GetServersDataListener.class.newInstance();
		} catch (Exception error) {
			error.printStackTrace();
		}
	}
	
}

package fr.badblock.bukkit.hub.v2.games;

import java.io.IOException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import fr.badblock.gameapi.utils.BukkitUtils;
import org.bukkit.Bukkit;

import fr.badblock.gameapi.BadblockPlugin;

public class GameLoader
{

	private static String[] string = new String[]
			{
					"fr.badblock.bukkit.hub.v2.games.type"
			};

	private static String[] string1 = new String[]
			{
					"fr.badblock.bukkit.hub.v2.games.listeners"
			};

	public static void load(BadblockPlugin plugin)
	{
		loadGames(plugin);
		loadGameListeners(plugin);
	}

	// Load tasks
	private static void loadGames(BadblockPlugin plugin)
	{
		try
		{
			URL url = plugin.getClass().getProtectionDomain().getCodeSource().getLocation();

			ZipInputStream zip = new ZipInputStream(url.openStream());
			ZipEntry entry = null;

			while ((entry = zip.getNextEntry()) != null)
			{
				String found = null;

				for(String path : string)
				{
					if(entry.getName().startsWith( path.replace(".", "/") ))
					{
						found  = entry.getName().replace("/", ".");
						break;
					}
				}

				if (found != null && entry.getName().endsWith(".class"))
				{
					try
					{
						String className = found.substring(0, found.length() - 6);

						Class<?> clazz = plugin.getClass().getClassLoader().loadClass(className);

						if (inheritFrom(clazz, HubGame.class))
						{
							instanciate(clazz);
						}
					}
					catch(Exception exception)
					{
						exception.printStackTrace();
					}
				}

			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			Bukkit.shutdown();
		}
	}

	private static void loadGameListeners(BadblockPlugin plugin)
	{
		try
		{
			BukkitUtils.instanciateListenersAndCommandsFrom(plugin, string1);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			Bukkit.shutdown();
		}
	}

	private static Object instanciate(Class<?> clazz) throws Exception
	{
		try
		{
			return clazz.getConstructor().newInstance();
		}
		catch(NoSuchMethodException exception)
		{
			return null;
		}
	}

	private static boolean inheritFrom(Class<?> clazz, Class<?> from)
	{
		while (clazz != Object.class)
		{
			if(clazz == from)
			{
				return true;
			}

			clazz = clazz.getSuperclass();
		}

		return false;
	}

}
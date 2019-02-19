package fr.badblock.bukkit.hub.v2.npc;

import java.sql.ResultSet;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.ArmorStand;

import fr.badblock.api.common.utils.TimeUtils;
import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.databases.SQLRequestType;
import fr.badblock.gameapi.utils.ConfigUtils;
import fr.badblock.gameapi.utils.general.Callback;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.md_5.bungee.api.ChatColor;

@AllArgsConstructor
@Data
public class Hologram
{

	private String		title;
	private String		displayName;
	private String		game;
	private String		field;
	private String		suffix;
	private Location	location;

	private transient	List<ArmorStand> holograms;

	public Hologram(ConfigurationSection section)
	{
		this.title = ChatColor.translateAlternateColorCodes('&', section.getString("title"));
		this.displayName = ChatColor.translateAlternateColorCodes('&', section.getString("displayName"));
		this.game = section.getString("game");
		this.field = section.getString("field");
		this.suffix = section.getString("suffix");
		this.location = ConfigUtils.convertStringToLocation(section.getString("location"));

		this.spawn();
	}

	public void spawn()
	{
		this.holograms = new ArrayList<>();
		Location loc = location.clone();
		this.holograms.add(NPC.spawnNametag(loc, title));
		loc = loc.add(0, -0.25, 0);
		this.holograms.add(NPC.spawnNametag(loc, displayName.replace("{date}", getDate().replace("_", " "))));

		for (int i = 1; i <= 10; i++)
		{
			loc = loc.add(0, -0.25, 0);
			this.holograms.add(NPC.spawnNametag(loc, ChatColor.LIGHT_PURPLE + "" + i + "e. " + ChatColor.YELLOW + "Inconnu " + ChatColor.GRAY + "(?" + suffix + ")"));
		}

		this.update();
	}

	public void update()
	{
		// Updater thread
		new Thread()
		{
			@Override
			public void run()
			{
				while (true)
				{
					GameAPI.getAPI().getSqlDatabase().call("SELECT playerName, " + field + " FROM " + game + "_" + getDate() +
							" ORDER BY " + field + " DESC LIMIT 10;", SQLRequestType.QUERY, new Callback<ResultSet>()
					{

						@Override
						public void done(ResultSet result, Throwable error)
						{
							List<Entry<String, Long>> list = new ArrayList<>();

							try
							{
								while (result.next())
								{
									String playerName = result.getString("playerName");
									long data = result.getLong(field);

									list.add(new SimpleEntry<>(playerName, data));
								}
							}
							catch (Exception err)
							{
								err.printStackTrace();
							}

							Bukkit.getScheduler().runTask(BadBlockHub.getInstance(), new Runnable()
							{
								@Override
								public void run()
								{
									int index = 1;
									for (Entry<String, Long> entry : list)
									{
										index++;
										int rank = index - 1;
										holograms.get(index).setCustomName(ChatColor.LIGHT_PURPLE + "" + rank + "e. " + ChatColor.YELLOW + entry.getKey() + " " + ChatColor.GRAY + "(" + entry.getValue() + suffix + ")");
									}
								}
							});
						}

					});
					TimeUtils.sleepInSeconds(60);
				}
			}
		}.start();
	}

	public static String getDate()
	{
		Date date = new Date();
		@SuppressWarnings("deprecation")
		String month = DateFormatSymbols.getInstance(Locale.FRENCH).getMonths()[date.getMonth()];
		SimpleDateFormat ffr = new SimpleDateFormat("yyyy", new Locale("fr"));
		String year = ffr.format(date);
		return month + "_" + year;
	}

}
package fr.badblock.bukkit.hub.v2.tasks;

import java.util.ArrayList;
import java.util.List;

import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.config.configs.BossBarsConfig;
import fr.badblock.bukkit.hub.v2.players.addons.BossBar;
import fr.badblock.bukkit.hub.v2.tags.TagManager;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.players.bossbars.BossBarStyle;
import fr.badblock.gameapi.utils.BukkitUtils;

public class BossBarsUpdateTask extends _HubTask
{

	private int 		id;
	private int			ticks;
	private boolean		reverse;
	private float		percent;

	public BossBarsUpdateTask()
	{
		super (true, 1, 1);
	}

	@Override
	public void run()
	{
		update();
	}

	/**
	 * List<String> bossBar = Arrays.asList(GameAPI.i18n().get(Locale.FRENCH_FRANCE, "hub.bossbar"));
		if (!reverse && percent < 1.0f) percent += 0.01f;
		else if (reverse && percent > 0.0f) percent -= 0.01;
		else if ((reverse && percent <= 0.0f) || (!reverse && percent >= 1.0f)) {
			id++;
			reverse = !reverse;
		}
		if (percent < 0.0f) percent = 0.0f;
		else if (percent > 1.0f) percent = 1.0f;
		if (id > bossBar.size() - 1) id = 0;
		for (BadblockPlayer player : BukkitUtils.getPlayers())
			player.addBossBar("hub", player.getTranslatedMessage("hub.bossbar")[id], percent, get(player.getTranslatedMessage("hub.bossbarcolor")[id]), BossBarStyle.SOLID);
	 */

	private void update()
	{
		BossBarsConfig bossBarsConfig = ConfigLoader.getBossBars();

		List<BossBar> bossBars = new ArrayList<>(bossBarsConfig.getBossBars().values());
		BossBar bossBar = bossBars.get(id);

		boolean updated = false;

		if (bossBar != null)
		{
			if (ticks >= bossBar.getTicks())
			{
				id++;
				updated = true;
			}
			else
			{
				reverse = ticks >= bossBar.getTicks() / 2;
				ticks++;
			}
		}

		if (id > bossBars.size())
		{
			id = 0;
			updated = true;
			reverse = false;
			percent = 0f;
			ticks = 0;
		}

		bossBar = bossBars.get(id);

		show(bossBar, updated);
	}

	private void show(BossBar bossBar, boolean updated)
	{
		// Percent adjustment
		if (!reverse)
		{
			percent = (ticks - (ticks / 2)) / (Math.max(1, bossBar.getTicks()) / 2);
		}
		else if (reverse && percent > 0.0f)
		{
			percent = (Math.max(1, bossBar.getTicks()) / 2) / (ticks - (ticks / 2));
		}

		// Percent limit
		if (percent < 0.0f)
		{
			percent = 0.0f;
		}
		else if (percent > 1.0f)
		{
			percent = 1.0f;
		}

		for (BadblockPlayer player : BukkitUtils.getAllPlayers())
		{
			String line = player.getTranslatedMessage(bossBar.getI18nKey())[0];

			// Tagify
			TagManager tagManager = TagManager.getInstance();
			line = tagManager.tagify(player, line);

			// Send bossbar
			player.addBossBar("hub", line, percent, bossBar.getColor(), BossBarStyle.SOLID);
		}
	}

}
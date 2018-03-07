package fr.badblock.bukkit.hub.v2.players.addons;

import fr.badblock.gameapi.players.bossbars.BossBarColor;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BossBar
{

	private String			i18nKey;
	private int				ticks;
	private BossBarColor	color;
	
}

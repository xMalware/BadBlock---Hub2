package fr.badblock.bukkit.hub.v2.config;

import fr.badblock.bukkit.hub.v2.config.configs.ActionBarsConfig;
import fr.badblock.bukkit.hub.v2.config.configs.BossBarsConfig;
import fr.badblock.bukkit.hub.v2.config.configs.FeaturesConfig;
import fr.badblock.bukkit.hub.v2.config.configs.GameHubConfig;
import fr.badblock.bukkit.hub.v2.config.configs.HatConfig;
import fr.badblock.bukkit.hub.v2.config.configs.HubLocationsConfig;
import fr.badblock.bukkit.hub.v2.config.configs.ShopConfig;
import fr.badblock.bukkit.hub.v2.config.configs.SwitcherConfig;
import fr.badblock.gameapi.BadblockPlugin;
import lombok.Getter;
import lombok.Setter;

public class ConfigLoader 
{

	@Getter @Setter public static HubLocationsConfig	loc;
	@Getter @Setter public static ShopConfig				shop;
	@Getter @Setter public static FeaturesConfig		features;
	@Getter @Setter public static ActionBarsConfig		actionBars;
	@Getter @Setter public static BossBarsConfig		bossBars;
	@Getter @Setter public static HatConfig		hat;
	@Getter @Setter public static SwitcherConfig		switchers;
	@Getter @Setter public static GameHubConfig		gameHub;

	public static void load(BadblockPlugin plugin)
	{
		setLoc(new HubLocationsConfig(plugin, "locations"));
		setFeatures(new FeaturesConfig(plugin, "features"));
		setShop(new ShopConfig(plugin, "shop"));
		setActionBars(new ActionBarsConfig(plugin, "actionBars"));
		setBossBars(new BossBarsConfig(plugin, "bossBars"));
		setHat(new HatConfig(plugin, "cosmetics_hats"));
		setSwitchers(new SwitcherConfig(plugin, "switchers"));
		setGameHub(new GameHubConfig(plugin, "gameHub"));
	}

}

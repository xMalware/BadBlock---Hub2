package fr.badblock.bukkit.hub.v2.cosmetics.features.types;

import fr.badblock.bukkit.hub.v2.cosmetics.features.Feature;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.CustomDisguise;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.DisguiseAdminMCSM;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Getter;

@Getter
public enum DisguiseFeatures implements IFeatureWorker
{

	AdminMCSM(new DisguiseAdminMCSM(null));
	
	private CustomDisguise customDisguise;
	
	DisguiseFeatures(CustomDisguise customDisguise)
	{
		setCustomDisguise(customDisguise);
	}
	
	public void setCustomDisguise(CustomDisguise customDisguise)
	{
		this.customDisguise = customDisguise;
	}

	@Override
	public void work(BadblockPlayer player)
	{
		customDisguise.own(player);
		customDisguise.disguise();
	}
	
	public static void work(BadblockPlayer player, Feature feature)
	{
		String name = feature.getName();
		DisguiseFeatures finalFeature = null;
		for (DisguiseFeatures disguiseFeature : values())
		{
			if (disguiseFeature.name().equalsIgnoreCase(name))
			{
				finalFeature = disguiseFeature;
				break;
			}
		}
		if (finalFeature == null)
		{
			return;
		}
		finalFeature.work(player);
	}
	
}

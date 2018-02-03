package fr.badblock.bukkit.hub.v2.cosmetics.features;

import lombok.Getter;

@Getter
public enum FeatureType
{

	DISGUISE,
	GADGET,
	MOUNT,
	PARTICLE,
	PET;
	
	public static FeatureType get(String byName)
	{
		for (FeatureType featureType : values())
		{
			if (featureType.name().equalsIgnoreCase(byName))
			{
				return featureType;
			}
		}
		return null;
	}
	
}

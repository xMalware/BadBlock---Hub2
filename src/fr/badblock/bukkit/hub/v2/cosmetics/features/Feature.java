package fr.badblock.bukkit.hub.v2.cosmetics.features;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Data
public class Feature
{

	private FeatureType		type;
	private String			name;
	private int				badcoinsNeeded;
	private int				shopPointsNeeded;
	private int				levelNeeded;
	private long			expire;
	private FeatureNeeded	needed;

}

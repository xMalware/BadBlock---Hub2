package fr.badblock.bukkit.hub.v2.cosmetics.features;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FeatureNeeded
{

	private boolean		buyable;
	private boolean		everyoneHaveThis;
	private String[]	permissions;
	
}

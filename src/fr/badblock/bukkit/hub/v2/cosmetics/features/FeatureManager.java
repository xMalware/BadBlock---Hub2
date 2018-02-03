package fr.badblock.bukkit.hub.v2.cosmetics.features;

import java.util.ArrayList;
import java.util.List;

import fr.badblock.bukkit.hub.v2.players.HubStoredPlayer;
import lombok.Getter;
import lombok.Setter;

public class FeatureManager
{

	@Getter @Setter
	private static FeatureManager	instance	= new FeatureManager();
	
	public void addFeature(HubStoredPlayer hubStoredPlayer, Feature feature)
	{
		long start = System.currentTimeMillis();
		
		// Expire set
		long expire = -1;
		if (feature.getExpire() > 0)
		{
			expire = start + (feature.getExpire() * 1000L);
		}
		
		// Add owned feature
		OwnedFeature ownedFeature = new OwnedFeature(feature, start, expire);
		List<OwnedFeature> ownedFeatures = hubStoredPlayer.getFeatures().get(feature.getType());
		if (ownedFeatures == null)
		{
			ownedFeatures = new ArrayList<>();
		}
		ownedFeatures.add(ownedFeature);
		hubStoredPlayer.getFeatures().put(feature.getType(), ownedFeatures);
	}
	
}

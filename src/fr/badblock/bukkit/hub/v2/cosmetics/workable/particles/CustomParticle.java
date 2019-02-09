package fr.badblock.bukkit.hub.v2.cosmetics.workable.particles;

import java.lang.reflect.Constructor;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.bukkit.hub.v2.utils.effects.Effect;
import fr.badblock.bukkit.hub.v2.utils.effects.EffectManager;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Data;
import lombok.Getter;

@Data
public abstract class CustomParticle
{

	@Getter
	private static EffectManager effectManager = new EffectManager(BadBlockHub.getInstance());

	public abstract Class<? extends Effect> getEffect();

	public void start(BadblockPlayer player)
	{
		try
		{
			Constructor<? extends Effect> ctor = getEffect().getDeclaredConstructor(EffectManager.class);
			ctor.setAccessible(true);
			Effect effect = (Effect) ctor.newInstance(getEffectManager());
			
			HubPlayer hubPlayer = HubPlayer.get(player);
			
			if (hubPlayer == null)
			{
				return;
			}
	        System.out.println("Feature  > F");
			
			hubPlayer.setEffect(effect);
			effect.setEntity(player);
			effect.start();
		}
		catch (Exception error)
		{
			error.printStackTrace();
		}
	}

}

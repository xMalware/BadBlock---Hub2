package fr.badblock.bukkit.hub.v2.listeners.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.disguise.Disguise;
import fr.badblock.gameapi.particles.ParticleEffect;
import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class PlayerMooveListener extends BadListener{
	
	ParticleEffect effect;
	Disguise disguise;
	
	@EventHandler
	public void move(PlayerMoveEvent e) {
		BadblockPlayer player = (BadblockPlayer) e.getPlayer();
		if(player.isDisguised()) {
			switch(disguise.getEntityType()) {
			
			case CREEPER:
				effect.setType(ParticleEffectType.SMOKE_NORMAL);
			    effect.setAmount(1);
			    break;
			    
			case PIG:
				effect.setType(ParticleEffectType.FLAME);
				effect.setAmount(1);
				break;
				
			case SQUID:
				effect.setType(ParticleEffectType.WATER_BUBBLE);
				effect.setAmount(1);
				break;
				
			case BOAT:
				effect.setType(ParticleEffectType.WATER_BUBBLE);
				effect.setAmount(3);
				break;
				
			case SLIME:
				effect.setType(ParticleEffectType.SLIME);
				effect.setAmount(5);
				break;
				
			case SNOWMAN:
				effect.setType(ParticleEffectType.SNOW_SHOVEL);
				effect.setAmount(3);
				break;
				
			case BLAZE:
				effect.setType(ParticleEffectType.LAVA);
				effect.setAmount(3);
				break;
				
			case HORSE:
				effect.setType(ParticleEffectType.FLAME);
				effect.setAmount(1);
				break;
				
			case MAGMA_CUBE:
				effect.setType(ParticleEffectType.FLAME);
				effect.setAmount(2);
				break;
				
			case ARROW:
				effect.setType(ParticleEffectType.FLAME);
				effect.setAmount(1);
				break;
				
			case ENDER_SIGNAL:
				effect.setType(ParticleEffectType.PORTAL);
				effect.setAmount(3);
				break;
				
			case GUARDIAN:
				effect.setType(ParticleEffectType.WATER_BUBBLE);
				effect.setAmount(3);
				break;
				
			case PIG_ZOMBIE:
				effect.setType(ParticleEffectType.DRIP_LAVA);
				effect.setAmount(5);
				break;
				
			case MUSHROOM_COW:
				effect.setType(ParticleEffectType.VILLAGER_ANGRY);
				effect.setAmount(3);
				break;
				
			case VILLAGER:
				effect.setType(ParticleEffectType.SMOKE_NORMAL);
				effect.setAmount(1);
				break;
				
			case WOLF:
				effect.setType(ParticleEffectType.CLOUD);
				effect.setAmount(5);
				break;
				
				
			case FIREWORK:
				effect.setType(ParticleEffectType.FIREWORKS_SPARK);
				effect.setAmount(1);
				break;
				
			case GIANT:
				effect.setType(ParticleEffectType.LAVA);
				effect.setAmount(3);
				break;
				
			case ENDERMAN:	
				effect.setType(ParticleEffectType.PORTAL);
				effect.setAmount(4);
				break;
				
			case SNOWBALL:
				effect.setType(ParticleEffectType.SNOWBALL);
				effect.setAmount(3);
				break;
				
			case WITHER_SKULL:
				effect.setType(ParticleEffectType.ENCHANTMENT_TABLE);
				effect.setAmount(3);
				break;
				
			case ENDERMITE:
				effect.setType(ParticleEffectType.PORTAL);
				effect.setAmount(3);
				break;
				
			case BAT:
				effect.setType(ParticleEffectType.SMOKE_NORMAL);
				effect.setAmount(1);
				break;
				
			case FIREBALL:
				effect.setType(ParticleEffectType.FLAME);
				effect.setAmount(3);
				break;
				
			case SMALL_FIREBALL:
				effect.setType(ParticleEffectType.FLAME);
				effect.setAmount(1);
				break;
				
			case ENDER_PEARL:
				effect.setType(ParticleEffectType.PORTAL);
				effect.setAmount(3);
				break;
				
			case IRON_GOLEM:
				effect.setType(ParticleEffectType.LAVA);
				effect.setAmount(2);
				break;
				
			case WITCH:
				effect.setType(ParticleEffectType.SPELL_WITCH);
				effect.setAmount(4);
				break;
				
			case ENDER_CRYSTAL:
				effect.setType(ParticleEffectType.PORTAL);
				effect.setAmount(5);
				break;
				
			case ZOMBIE:
				effect.setType(ParticleEffectType.REDSTONE);
				effect.setAmount(2);
				break;
				
			case COW:
				effect.setType(ParticleEffectType.SNOW_SHOVEL);
				effect.setAmount(2);
				break;
				
			case GHAST:
				effect.setType(ParticleEffectType.LAVA);
				effect.setAmount(3);
				break;
				
				default:
					break;
			}
		}
	}

}

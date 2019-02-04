package fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.utils.ParticleEffect;
import fr.badblock.gameapi.players.BadblockPlayer;
import net.minecraft.server.v1_8_R3.EntityCreature;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftCreeper;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Collection;

public class CreeperExploser extends AbstractGadgets{

    public CreeperExploser() {
        super("§8Creeper Explosif", new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.CREEPER.ordinal()), 4);
    }

    @Override
    public void equip(BadblockPlayer badblockPlayer) {

    }

    @Override
    public void unequip(BadblockPlayer badblockPlayer) {

    }

    @Override
    public boolean use(BadblockPlayer badblockPlayer, ItemStack item, Action action) {
        Collection<Entity> entities = badblockPlayer.getLocation().getWorld().getNearbyEntities(badblockPlayer.getLocation(), 5, 2, 5);
        entities.remove(badblockPlayer);

        if(entities.isEmpty()) {
            System.out.println(entities.size());
            badblockPlayer.sendMessage("§cAucun joueur à proximité !");
            return false;
        }

        LivingEntity entityLiving = null;
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity) {
                System.out.println(entity.getType());
                entityLiving = (LivingEntity) entity;
                break;
            }
        }

        if(entityLiving == null){
            badblockPlayer.sendMessage("§cAucun joueur trouvé !");
            return false;
        }

        Creeper creeper = (Creeper) badblockPlayer.getLocation().getWorld().spawnEntity(badblockPlayer.getLocation(), EntityType.CREEPER);
        creeper.setMetadata("owner-id", new FixedMetadataValue(BadBlockHub.getInstance(), badblockPlayer.getName()));
        creeper.setPowered(true);
        EntityCreature nmsEntity = ((CraftCreeper) creeper).getHandle();
        nmsEntity.setGoalTarget(((CraftLivingEntity)entityLiving).getHandle(), null, false);

        Bukkit.getServer().getScheduler().runTaskTimer(BadBlockHub.getInstance(), () -> {
            if(!creeper.isDead())
                ParticleEffect.HEART.display(0.25F, 0.5F, 0.25F, 1.0F, 6, creeper.getLocation(), 20.0D);
        }, 0, 5);

        return true;
    }

    @Override
    public void handleInteraction(Entity from, Entity to) {
        //TODO PAS FINIIIIIIIIIIIIIIIIII
    }

    @Override
    public int waitingTime() {
        return 5000;
    }
}

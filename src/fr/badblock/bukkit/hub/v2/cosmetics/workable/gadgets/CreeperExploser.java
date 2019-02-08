package fr.badblock.bukkit.hub.v2.cosmetics.workable.gadgets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftCreeper;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.utils.ParticleEffect;
import fr.badblock.gameapi.players.BadblockPlayer;
import net.minecraft.server.v1_8_R3.EntityCreature;

public class CreeperExploser extends AbstractGadgets{

    private BukkitTask task;

    public CreeperExploser(String internalName) {
        super(internalName, new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.CREEPER.ordinal()), 4);
    }

    @Override
    public void equip(BadblockPlayer badblockPlayer) {

    }

    @Override
    public void unequip(BadblockPlayer badblockPlayer) {

    }

    @Override
    public boolean use(BadblockPlayer badblockPlayer, ItemStack item, Action action) {
        Collection<Entity> entities = badblockPlayer.getLocation().getWorld().getNearbyEntities(badblockPlayer.getLocation(), 10, 8, 10);
        entities.remove(badblockPlayer);

        if(entities.isEmpty()) {
            System.out.println(entities.size());
            badblockPlayer.sendMessage("§cAucun joueur à proximité !");
            return false;
        }

        LivingEntity entityLiving = null;
        for (Entity entity : entities) {
            if (entity instanceof Player) {
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
        System.out.println(creeper.getMetadata("owner-id").get(0).asString());
        creeper.setPowered(true);

        EntityCreature nmsEntity = ((CraftCreeper) creeper).getHandle();
        nmsEntity.setGoalTarget(((CraftLivingEntity)entityLiving).getHandle(), null, false);

        task = Bukkit.getServer().getScheduler().runTaskTimer(BadBlockHub.getInstance(), () -> {
            if(!creeper.isDead())
                ParticleEffect.HEART.display(0.25F, 0.5F, 0.25F, 1.0F, 6, creeper.getLocation(), 20.0D);
        }, 0, 5);

        return true;
    }

    @Override
    public void handleInteraction(Entity from, Entity to) {
            if(to instanceof Creeper){
                task.cancel();

                List<Item> items = new ArrayList<>();
                ItemStack dye = new ItemStack(Material.INK_SACK, 1, (short) 1);
                Random random = new Random();

                for(int i = 0; i <= 30; i++){
                    Item item = to.getWorld().dropItemNaturally(to.getLocation().add(0, 1.2, 0), dye);
                    item.setVelocity(new Vector(random.nextInt(2) - 1.75, 1.5, random.nextInt(2) - 1.75));
                    items.add(item);
                }

                Creeper creeper = (Creeper) to;
                to.getWorld().strikeLightningEffect(creeper.getLocation());

                new BukkitRunnable(){

                    @Override
                    public void run() {
                        items.forEach(Item::remove);
                    }
                }.runTaskLater(BadBlockHub.getInstance(), 60);

            }
    }

    @Override
    public int waitingTime() {
        return 5000;
    }
}

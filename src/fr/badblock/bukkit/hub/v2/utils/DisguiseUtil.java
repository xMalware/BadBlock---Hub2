package fr.badblock.bukkit.hub.v2.utils;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.gameapi.players.BadblockPlayer;
import io.netty.channel.Channel;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class DisguiseUtil implements Listener {

    private static Map<UUID, DisguiseUtil> disguise = new HashMap<>();
    private static List<Integer> ids = new ArrayList<>();
    private static Plugin instance = BadBlockHub.getInstance();

    private enum OBJECT_ID {
        BOAT(1),
        DROPPED_ITEM(2),
        AREA_EFFECT_CLOUD(3),
        MINECART(10),
        PRIMED_TNT(50),
        ENDER_CRYSTAL(51),
        TIPPED_ARROW(60),
        SNOWBALL(61),
        EGG(62),
        FIREBALL(63),
        SMALL_FIREBALL(64),
        ENDER_PEARL(65),
        WITHER_SKULL(66),
        SHULKER_BULLET(67),
        FALLING_BLOCK(70),
        ITEM_FRAME(71),
        SPLASH_POTION(73),
        THROWN_EXP_BOTTLE(75),
        FIREWORK(76),
        LEASH_HITCH(77),
        ARMOR_STAND(78),
        FISHING_HOOK(90),
        SPECTRAL_ARROW(91);

        private int id;

        private OBJECT_ID(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static int getId(String name) {
            int id = 0;
            for (OBJECT_ID object : OBJECT_ID.values()) {
                if (object.name().equalsIgnoreCase(name)) {
                    id = object.getId();
                    break;
                }
            }
            return id;
        }
    }

    /*
     * Class
     */
    private static Class<?> craftItemStackClass = Reflection.getClass("{obc}.inventory.CraftItemStack");
    private static Class<?> packetEquipClass = Reflection.getClass("{nms}.PacketPlayOutEntityEquipment");
    private static Class<?> packetDestroyClass = Reflection.getClass("{nms}.PacketPlayOutEntityDestroy");
    private static Class<?> nmsWorldField = Reflection.getClass("{nms}.World");
    private Class<?> enumItemSlotClass;
    private static Class<?> packetSpawnLivingClass = Reflection.getClass("{nms}.PacketPlayOutSpawnEntityLiving");
    private static Class<?> packetSpawnEntityClass = Reflection.getClass("{nms}.PacketPlayOutSpawnEntity");
    private static Class<?> packetSpawnNamedClass = Reflection.getClass("{nms}.PacketPlayOutNamedEntitySpawn");
    private static Class<?> entityHumanClass = Reflection.getClass("{nms}.EntityHuman");
    private static Class<?> entityBatClass = Reflection.getClass("{nms}.EntityBat");
    private static Class<?> entityAgeableClass = Reflection.getClass("{nms}.EntityAgeable");
    private static Class<?> entityLivingClass = Reflection.getClass("{nms}.EntityLiving");
    private static Class<?> entityClass = Reflection.getClass("{nms}.Entity");
    private static Class<?> itemStackClass = Reflection.getMinecraftClass("ItemStack");
    private Class<?> entityTypeClass;

    /**
     * Reflection methods
     */
    private static Reflection.MethodInvoker getHandleMethod = Reflection.getMethod("{obc}.entity.CraftPlayer", "getHandle");
    private static Reflection.MethodInvoker asNMSCopyMethod = Reflection.getMethod(craftItemStackClass, "asNMSCopy", ItemStack.class);
    private static Reflection.MethodInvoker sendPacket = Reflection.getMethod("{nms}.PlayerConnection", "sendPacket", Object.class);
    private static Reflection.MethodInvoker getHandleWorldMethod = Reflection.getMethod("{obc}.CraftWorld", "getHandle");
    private static Reflection.MethodInvoker setPositionMethod = Reflection.getMethod(entityClass, "setLocation", double.class, double.class, double.class, float.class, float.class);
    private static Reflection.MethodInvoker setAgeMethod = Reflection.getMethod(entityAgeableClass, "setAge", int.class);
    private static Reflection.MethodInvoker setCustomeNameMethod = Reflection.getMethod(entityClass, "setCustomName", String.class);

    /**
     * Field accessors
     */
    private static Reflection.FieldAccessor<?> playerConnectionField = Reflection.getField("{nms}.EntityPlayer", "playerConnection", Object.class);
    private static Reflection.FieldAccessor<?> playerIDField = Reflection.getField(Reflection.getMinecraftClass("Entity"), int.class, 2);
    private static Reflection.FieldAccessor<?> packetEquipPlayerIDField = Reflection.getField(packetEquipClass, int.class, 0);
    private Reflection.FieldAccessor<?> packetEquipItemSlotField;
    private static Reflection.FieldAccessor<?> packetEquipItemField = Reflection.getField(packetEquipClass, itemStackClass, 0);
    private static Reflection.FieldAccessor<?> packetDestroyPlayerIDField = Reflection.getField(packetDestroyClass, Object.class, 0);

    /*
     * Constructor
     */
    private static Reflection.ConstructorInvoker packetEquipConstructor = Reflection.getConstructor(packetEquipClass);
    private static Reflection.ConstructorInvoker packetSpawnConstructor;
    private static Reflection.ConstructorInvoker packetDestroyConstructor = Reflection.getConstructor(packetDestroyClass);
    private static Reflection.ConstructorInvoker packetSpawnHumanConstructor = Reflection.getConstructor(packetSpawnNamedClass, entityHumanClass);

    /*
     * Player info
     */
    private Player p;
    private Object nmsPlayer;
    private Object playerConnection;
    private int playerID = 0;
    private ItemStack[] equipments;
    private boolean disguised;
    private EntityType entityType;
    private Object entity;
    private Player target;
    private boolean isEntityLiving;
    private Collection<? extends Player> targets;
    private Object packetSpawn;

    /*
     * Other info
     */
    private boolean is1_9OrNewer = is1_9();


    /**
     * Packets
     */
    private Object packetDestroy;


    /**
     * Setup constructor..
     *
     * @param entityType
     * @param p
     */
    public DisguiseUtil(EntityType entityType, Player p) {

        if (getDisguised().containsKey(p.getUniqueId()))
            getDisguised().get(p.getUniqueId()).removeDisguise();

        this.p = p;
        this.entityType = entityType;

        if (entityType == EntityType.FIREWORK)
            this.entityTypeClass = Reflection.getClass("{nms}.EntityFireworks");
        else if (entityType == EntityType.MINECART)
            this.entityTypeClass = Reflection.getClass("{nms}.EntityMinecartRideable");
        else
            this.entityTypeClass = Reflection.getClass("{nms}.Entity" + entityType.getEntityClass().getSimpleName());

        /**
         * Players info
         */
        this.nmsPlayer = getHandleMethod.invoke(p);
        this.playerConnection = playerConnectionField.get(nmsPlayer);
        this.playerID = (Integer) playerIDField.get(nmsPlayer);
        System.out.println("Add: "+playerID);
        ids.add(playerID);

        /*
         * Packet Equipment
         */
        PlayerInventory inv = p.getInventory();
        ItemStack[] equipSub = {inv.getBoots(), inv.getLeggings(), inv.getChestplate(), inv.getHelmet()};
        this.equipments = equipSub;
        if (is1_9OrNewer) {
            this.enumItemSlotClass = Reflection.getMinecraftClass("EnumItemSlot");
            this.packetEquipItemSlotField = Reflection.getField(packetEquipClass, enumItemSlotClass, 0);
        } else
            this.packetEquipItemSlotField = Reflection.getField(packetEquipClass, int.class, 1);

        /*
         * Packet Entity
         */

        Reflection.ConstructorInvoker packetEntityConstructor = Reflection.getConstructor(entityTypeClass, nmsWorldField);
        packetEntityConstructor = Reflection.getConstructor(entityTypeClass, nmsWorldField);
        entity = packetEntityConstructor.invoke(getHandleWorldMethod.invoke(p.getWorld()));

        //EntityBat debug...
        if (entityType == EntityType.BAT) {
            Reflection.MethodInvoker setAsleep = Reflection.getMethod(entityBatClass, "setAsleep", boolean.class);
            setAsleep.invoke(entity, false);
        }
        setPositionMethod.invoke(entity, p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());

        //Is entityLiving ?
        isEntityLiving = entityLivingClass.isAssignableFrom(entityTypeClass);
        packetSpawnConstructor = isEntityLiving ? Reflection.getConstructor(packetSpawnLivingClass, entityLivingClass) : Reflection.getConstructor(packetSpawnEntityClass, entityClass, int.class);

        packetSpawn = isEntityLiving ? packetSpawnConstructor.invoke(entity) : packetSpawnConstructor.invoke(entity, OBJECT_ID.getId(entityType.name()));

        Reflection.getField(packetSpawn.getClass(), int.class, 0).set(packetSpawn, playerID);

        /*
         * Packet destroy
         */
        packetDestroy = packetDestroyConstructor.invoke();
        int[] id = {playerID};
        packetDestroyPlayerIDField.set(packetDestroy, id);

        this.disguised = false;
        disguise.put(p.getUniqueId(), this);
    }


    /**
     * Set size of magma cube or slime
     *
     * @param size
     */
    public void setSize(int size) {
        if (entityType == EntityType.SLIME || entityType == EntityType.MAGMA_CUBE) {
            Reflection.MethodInvoker setS = Reflection.getMethod(entityTypeClass, "setSize", int.class);
            setS.invoke(entity, size);
        }
    }

    /**
     * set Entity age to -1
     */
    public void setBaby() {
        if (entityAgeableClass.isAssignableFrom(entityTypeClass)) {
            setAgeMethod.invoke(entity, -1);
        }
    }

    /**
     * set Entity customName
     *
     * @param name
     */
    public void setCustomName(String name) {
        setCustomeNameMethod.invoke(entity, name);
    }

    @SuppressWarnings("deprecation")
    public void setBlock(Material type, byte data) {
        if (entityType == EntityType.FALLING_BLOCK) {
            Reflection.getField(packetSpawn.getClass(), int.class, 7).set(packetSpawn, type.getId() + (data << 12));
        }
    }

    @SuppressWarnings("deprecation")
    public void setBlock(Material type) {
        if (entityType == EntityType.FALLING_BLOCK) {
            Reflection.getField(packetSpawn.getClass(), int.class, 7).set(packetSpawn, type.getId() + (0 << 12));
        }
    }


    /**
     * Set entity type
     *
     * @author cocoraid
     */
    public enum DisguiseType {
        WITHER_SKELETON(1),
        LIBRARIAN_VILLAGER(1),
        PRIEST_VILLAGER(2),
        BLACKSMITH_VILLAGER(3),
        POWERED_CREEPER(-1),
        IGNITED_CREEPER(-1),
        BUTCHER_VILLAGER(4);
        private int id;

        private DisguiseType(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }
    }

    private enum CREEPER_VERSION {

        v1_9_R2("dd"),
        v1_9_R1("dc"),
        v1_8_R3("co"),
        v1_8_R2("co"),
        v1_8_R1("cm");

        private String method;

        private CREEPER_VERSION(String method) {
            this.method = method;
        }

        public String getMethod() {
            return this.method;
        }
    }

    @SuppressWarnings("incomplete-switch")
    public void setDisguiseType(DisguiseType type) {
        switch (entityType) {
            case SKELETON:
                Reflection.MethodInvoker setS = Reflection.getMethod(entityTypeClass, "setSkeletonType", int.class);
                setS.invoke(entity, type.getId());
                break;
            case VILLAGER:
                Reflection.MethodInvoker setP = Reflection.getMethod(entityTypeClass, "setProfession", int.class);
                setP.invoke(entity, type.getId());
                break;
            case CREEPER:
                if (type == DisguiseType.POWERED_CREEPER) {
                    Reflection.MethodInvoker setPowered = Reflection.getMethod(entityTypeClass, "setPowered", boolean.class);
                    setPowered.invoke(entity, true);
                } else if (type == DisguiseType.IGNITED_CREEPER) {
                    String version = getPackageName();
                    for (CREEPER_VERSION cv : CREEPER_VERSION.values()) {
                        if (cv.name().equalsIgnoreCase(version)) {
                            Reflection.MethodInvoker ignit = Reflection.getMethod(entityTypeClass, cv.getMethod());
                            ignit.invoke(entity);
                            break;
                        }
                    }
                }
                break;
        }
    }


    /**
     *
     */
    public void disguisePlayer(Collection<? extends Player> collection) {
        hideEntity(collection);
        sendPacket(packetSpawn, collection);
        setEquipment(collection);
        disguised = true;
        targets = collection;
    }

    /**
     * @param target
     */
    public void disguisePlayer(Player target) {
        hideEntity(target);
        sendPacket(packetSpawn, target);
        setEquipment(target);
        disguised = true;
        this.target = target;
    }

    /**
     * Set equipment for a player...
     *
     * @param target
     */

    private void setEquipment(Player target) {
        sendPacket(buildEquipmentPacket(0, p.getInventory().getItemInHand()), target);
        if (is1_9OrNewer) {
            /*On 1.9: 0 = Main Hand ; 1 = Off Hand ; 2 = Boots ; 3 = Leggings ; 4 = Chest ; 5 = Helmet */
            sendPacket(buildEquipmentPacket(2, p.getInventory().getBoots()), target);
            sendPacket(buildEquipmentPacket(3, p.getInventory().getLeggings()), target);
            sendPacket(buildEquipmentPacket(4, p.getInventory().getChestplate()), target);
            sendPacket(buildEquipmentPacket(5, p.getInventory().getHelmet()), target);
        } else {
            /*On 1.8: 0 = Main Hand ; 1 = Boots ; 2 = Leggings ; 3 = Chest ; 4 = Helmet */
            for (int i = 1; i < equipments.length; i++) {
                sendPacket(buildEquipmentPacket(i, equipments[i - 1]), target);
            }
        }
    }

    /**
     * Set equipment for all players...
     */
    private void setEquipment(Collection<? extends Player> collection) {
        sendPacket(buildEquipmentPacket(0, p.getInventory().getItemInHand()), collection);
        if (is1_9OrNewer) {
            /*On 1.9: 0 = Main Hand ; 1 = Off Hand ; 2 = Boots ; 3 = Leggings ; 4 = Chest ; 5 = Helmet */
            sendPacket(buildEquipmentPacket(2, p.getInventory().getBoots()), collection);
            sendPacket(buildEquipmentPacket(3, p.getInventory().getLeggings()), collection);
            sendPacket(buildEquipmentPacket(4, p.getInventory().getChestplate()), collection);
            sendPacket(buildEquipmentPacket(5, p.getInventory().getHelmet()), collection);
        } else {
            /*On 1.8: 0 = Main Hand ; 1 = Boots ; 2 = Leggings ; 3 = Chest ; 4 = Helmet */
            for (int i = 1; i < equipments.length; i++) {
                sendPacket(buildEquipmentPacket(i, equipments[i - 1]), collection);
            }
        }
    }


    /**
     * Just a simple method to build the EquipmentPacket's constructor...
     *
     * @param slot
     * @param item
     * @return
     */
    private Object buildEquipmentPacket(int slot, ItemStack item) {
        Object packet = packetEquipConstructor.invoke();
        packetEquipPlayerIDField.set(packet, playerID);
        if (is1_9OrNewer) {
            packetEquipItemSlotField.set(packet, enumItemSlotClass.getEnumConstants()[slot]);
        } else
            packetEquipItemSlotField.set(packet, slot);
        packetEquipItemField.set(packet, asNMSCopyMethod.invoke(craftItemStackClass, item));
        return packet;
    }


    /**
     * Restore the player
     */
    public void removeDisguise() {
        Object packetSpawnNamed = packetSpawnHumanConstructor.invoke(nmsPlayer);
        if (this.target != null) {
            hideEntity(target);
            sendPacket(packetSpawnNamed, target);
            setEquipment(target);
        } else if (targets != null) {
            hideEntity(targets);
            sendPacket(packetSpawnNamed, targets);
            setEquipment(targets);
        } else {
            hideEntity(Bukkit.getOnlinePlayers());
            sendPacket(packetSpawnNamed, Bukkit.getOnlinePlayers());
            setEquipment(Bukkit.getOnlinePlayers());
        }
        remove();
        System.out.println("Remove: "+playerID);
        ids.remove(new Integer(playerID));
    }

    private void removePlayerDisguise() {
        Object packetSpawnNamed = packetSpawnHumanConstructor.invoke(nmsPlayer);
        if (this.target != null) {
            hideEntity(target);
            sendPacket(packetSpawnNamed, target);
            setEquipment(target);
        } else if (targets != null) {
            hideEntity(targets);
            sendPacket(packetSpawnNamed, targets);
            setEquipment(targets);
        } else {
            hideEntity(Bukkit.getOnlinePlayers());
            sendPacket(packetSpawnNamed, Bukkit.getOnlinePlayers());
            setEquipment(Bukkit.getOnlinePlayers());
        }
    }

    /**
     * Hide Entity for all players
     */
    private void hideEntity(Collection<? extends Player> collection) {
        sendPacket(packetDestroy, collection);
    }

    /**
     * Hide Entity for the target
     *
     * @param target
     */
    private void hideEntity(Player target) {
        sendPacket(packetDestroy, target);
    }


    /**
     * Send Packet Method
     *
     * @param packet
     * @param player
     */
    private void sendPacket(Object packet, Player player) {
        if (!player.equals(p)) {
            sendPacket.invoke(playerConnection, packet);
        }
    }

    /**
     * Send Packet Method
     *
     * @param packet
     * @param collection
     */
    private void sendPacket(Object packet, Collection<? extends Player> collection) {
        collection.stream().filter(cur -> !cur.equals(p)).forEach(cur ->
                sendPacket.invoke(playerConnectionField.get(getHandleMethod.invoke(cur)), packet));
    }

    private void remove() {
        getDisguised().remove(p.getUniqueId());
    }

    public boolean isDisguised() {
        return this.disguised;
    }

    public void setDisguised(boolean disguised) {
        this.disguised = disguised;
    }

    private boolean is1_9() {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        return packageName.substring(packageName.lastIndexOf('.') + 1).contains("1_9");
    }

    private String getPackageName() {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        return packageName.substring(packageName.lastIndexOf('.') + 1);
    }

    /**
     * Respawn player debug
     *
     * @author cocoraid
     */
    static class PacketEvent extends TinyProtocol.PacketListener {
        private Class<?> packet = Reflection.getMinecraftClass("PacketPlayOutNamedEntitySpawn");
        private Class<?> useEntityClass = Reflection.getClass("{nms}.PacketPlayInUseEntity");


        @Override
        public Object onPacketInAsync(final Player receiver, Channel channel, Object packet) {
            if (useEntityClass.isInstance(packet)) {
                int id = Reflection.getField(this.useEntityClass, int.class, 0).get(packet);
                System.out.println("Async: "+id);
                if (ids.contains(id))
                    return null;
            }
            return super.onPacketOutAsync(receiver, channel, packet);
        }

        @Override
        public Object onPacketOutAsync(final Player receiver, Channel channel, Object packet) {
            if (!this.packet.isInstance(packet))
                return packet;
            if (receiver != null) {
                if (DisguiseUtil.disguise.containsKey(receiver.getUniqueId())) {
                    Bukkit.getScheduler().runTask(BadBlockHub.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            if (instanceOf(receiver) != null)
                                instanceOf(receiver).disguisePlayer(receiver);
                        }
                    });
                }
            }
            return super.onPacketOutAsync(receiver, channel, packet);
        }
    }

    public static void register() {
        TinyProtocol.getTinyProtocol().registerListener(new PacketEvent());
        instance.getServer().getPluginManager().registerEvents(new Listener() {

            @EventHandler
            public void onPlayerQuitEvent(PlayerQuitEvent e) {
                BadblockPlayer p = (BadblockPlayer) e.getPlayer();
                DisguiseUtil d = DisguiseUtil.instanceOf(p);
                if (d == null) return;
                if (!d.isDisguised()) {
                    d.removeDisguise();
                    d.setDisguised(false);

                }
            }

            @EventHandler
            public void onDisable(PluginDisableEvent e) {
                if (e.getPlugin().equals(instance)) {
                    DisguiseUtil.disguise.values().forEach(disguise -> {
                        if (disguise.isDisguised())
                            disguise.removePlayerDisguise();
                    });
                }
            }
        }, instance);
    }

    /**
     * Get All player disguised
     *
     * @return Map<UUID       ,               DisguiseUtil>
     */
    public static Map<UUID, DisguiseUtil> getDisguised() {
        return disguise;
    }

    public static DisguiseUtil instanceOf(Player p) {
        return instanceOff(p.getUniqueId());
    }

    public static DisguiseUtil instanceOff(UUID uuid) {
        return disguise.getOrDefault(uuid, null);
    }

    /**
     * Get players who can see the disguise !
     *
     * @return List<Player>
     */
    public List<Player> getPlayersCanSee() {
        List<Player> canSee = new ArrayList<>();
        if (targets != null) {
            canSee.addAll(targets);
            canSee.remove(p);
        } else if (target != null)
            canSee.add(p);
        return canSee;
    }

}


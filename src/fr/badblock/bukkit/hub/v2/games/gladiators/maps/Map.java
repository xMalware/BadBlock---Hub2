package fr.badblock.bukkit.hub.v2.games.gladiators.maps;

import fr.badblock.bukkit.hub.v2.games.gladiators.GladiatorManager;
import fr.badblock.bukkit.hub.v2.games.gladiators.kits.KitsManager;
import fr.badblock.bukkit.hub.v2.games.utils.CustomPlayerInventory;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {

    private String name;
    private List<Sign> signLocations;
    private List<Location> positions;
    private List<Player> players;

    public Map(String name, List<Location> positions) {
        this.name = name;
        this.positions = positions;
        this.players = new ArrayList<>();
        this.signLocations = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Location getRandomLoc(){
        Random random = new Random();
        return positions.get(random.nextInt(positions.size()));
    }

    public List<Sign> getSignLocations() {
        return signLocations;
    }

    public boolean giveKit(Player player){
        List<ItemStack[]> s = KitsManager.loadKit(name);

        if(s == null || s.size() == 0){
            return false;
        }

        CustomPlayerInventory customPlayerInventory = new CustomPlayerInventory();
        customPlayerInventory.storeAndClearInventory(player);
        GladiatorManager.getInstance().getCustomInv().put(player, customPlayerInventory);

        player.getInventory().setContents(s.get(0));
        player.getInventory().setArmorContents(s.get(1));
        player.updateInventory();
        return true;
    }
}
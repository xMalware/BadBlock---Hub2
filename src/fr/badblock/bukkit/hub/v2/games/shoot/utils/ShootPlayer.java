package fr.badblock.bukkit.hub.v2.games.shoot.utils;

import fr.badblock.bukkit.hub.v2.games.shoot.ShootManager;
import fr.badblock.bukkit.hub.v2.games.utils.CustomPlayerInventory;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Toinetoine1 on 19/01/2019.
 */

public class ShootPlayer {

    private Player player;
    private boolean hasFinished;
    private CustomPlayerInventory customPlayerInventory;
    private Box box;

    public ShootPlayer(Player player, boolean hasFinished) {
        this.player = player;
        this.hasFinished = hasFinished;
        this.customPlayerInventory = new CustomPlayerInventory();
    }

    public Player getPlayer() {
        return player;
    }

    public CustomPlayerInventory getCustomPlayerInventory() {
        return customPlayerInventory;
    }

    public boolean isHasFinished() {
        return hasFinished;
    }

    public void setHasFinished(boolean hasFinished) {
        this.hasFinished = hasFinished;
    }

    public boolean assignBox() {
        List<Box> boxes = ShootManager.getInstance().getBoxes().stream().filter(box1 -> !box1.isTaken()).collect(Collectors.toList());

        if (boxes.size() >= 1) {
            box = boxes.get(0);
            box.setTaken(true);
            return true;
        } else
            return false;

    }

    public Box getBox() {
        return box;
    }
}

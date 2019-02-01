package fr.badblock.bukkit.hub.v2.games.spleef;


import fr.badblock.bukkit.hub.v2.games.utils.CustomPlayerInventory;

/**
 * Created by Toinetoine1 on 18/01/2019.
 */
public class SpleefPlayer {

    private String playerName;
    private boolean isDead;
    private boolean isCreativeMod;
    private CustomPlayerInventory customInv;

    public SpleefPlayer(String playerName, boolean isCreativeMod) {
        this.playerName = playerName;
        this.isDead = false;
        this.isCreativeMod = isCreativeMod;
        customInv = new CustomPlayerInventory();
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean isCreativeMod() {
        return isCreativeMod;
    }

    public void setCreativeMod(boolean creativeMod) {
        isCreativeMod = creativeMod;
    }

    public CustomPlayerInventory getCustomInv() {
        return customInv;
    }

    public String getPlayerName() {
        return playerName;
    }
}

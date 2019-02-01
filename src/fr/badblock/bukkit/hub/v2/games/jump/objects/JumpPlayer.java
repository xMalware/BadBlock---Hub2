package fr.badblock.bukkit.hub.v2.games.jump.objects;

import org.bukkit.entity.Player;

/**
 * Created by Toinetoine1 on 16/01/2019.
 */
public class JumpPlayer {

    private Player player;
    private int checkpoint;
    private int life;

    public JumpPlayer(Player player, int checkpoint, int life) {
        this.player = player;
        this.checkpoint = checkpoint;
        this.life = life;
    }

    public int getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(int checkpoint) {
        this.checkpoint = checkpoint;
    }

    public void removeLife() {
        this.life--;
    }

    public int getLife() {
        return life;
    }

    public Player getBukkitPlayer() {
        return player;
    }

}

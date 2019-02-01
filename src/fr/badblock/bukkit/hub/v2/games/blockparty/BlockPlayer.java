package fr.badblock.bukkit.hub.v2.games.blockparty;

import com.xxmicloxx.NoteBlockAPI.model.Playlist;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import fr.badblock.bukkit.hub.v2.BadBlockHub;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockPlayer {

    private Player player;
    private boolean isDead;
    private Playlist playlist;
    private RadioSongPlayer radioSongPlayer;

    public BlockPlayer(Player player, boolean isDead) {
        this.player = player;
        this.isDead = isDead;

        File[] files = new File(BadBlockHub.getInstance().getDataFolder(), "/songs/").listFiles();
        List<Song> songs = new ArrayList<>();
        for (File file : files) {
            songs.add(NBSDecoder.parse(file));
        }
        Collections.shuffle(songs);
        playlist = new Playlist(songs.toArray(new Song[songs.size()]));
        radioSongPlayer = new RadioSongPlayer(playlist);
        radioSongPlayer.setStereo(true);
        radioSongPlayer.addPlayer(player);
        radioSongPlayer.setPlaying(false);
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public RadioSongPlayer getRadioSongPlayer() {
        return radioSongPlayer;
    }
}

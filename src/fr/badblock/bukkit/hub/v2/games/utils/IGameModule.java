package fr.badblock.bukkit.hub.v2.games.utils;

/**
 * Created by Toinetoine1 on 13/01/2019.
 */
public interface IGameModule {

    void registerBukkitListener();
    void registerCommands();
    void loadConfig();

}

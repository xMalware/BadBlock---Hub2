package fr.badblock.bukkit.hub.v2.games.gladiators.maps;

import java.util.ArrayList;
import java.util.List;

public class MapManager {

    private static MapManager instance;
    private List<Map> maps;

    public MapManager() {
        instance = this;
        maps = new ArrayList<>();
    }

    public Map getMap(String name){
        Map map = null;
        for(Map map1 : maps){
            if(map1.getName().equals(name)){
                map = map1;
                break;
            }
        }

        return map;
    }

    public List<Map> getMaps() {
        return maps;
    }

    public static MapManager get() {
        return instance;
    }
}

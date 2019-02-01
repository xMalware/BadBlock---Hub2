package fr.badblock.bukkit.hub.v2.games.states;

public enum GameState {

    INGAME, WAITING, STARTING;

    private GameState parcours;

    public void setState(GameState state){
        parcours = state;
    }

    public boolean isState(GameState state){
        return parcours == state;
    }

}

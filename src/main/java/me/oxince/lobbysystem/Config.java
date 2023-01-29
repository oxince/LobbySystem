package me.oxince.lobbysystem;

import me.oxince.lobbysystem.enums.LobbyItems;
import me.oxince.lobbysystem.enums.LobbyLocations;

import java.util.HashMap;

public class Config {
    public static String mongoUrl = "mongodb://admin:@:27011/";
    public static String databaseName = "minecraft";
    public static String[] databaseCollections = { "lobby", "locations" };

    public static String serverLabel = "Volverix";
    public static String spawnLocation = "Spawn";

    public static HashMap<LobbyItems, String> lobbyItems = new HashMap<>();
    public static HashMap<LobbyLocations, String> lobbyLocations = new HashMap<>();

    public static void initialize() {
        lobbyItems.put(LobbyItems.COMPASS, "Kompass");
        lobbyItems.put(LobbyItems.LOBBY_SWITCHER, "Lobby Switcher");
        lobbyItems.put(LobbyItems.PROFILE, "Dein Profil");
        lobbyItems.put(LobbyItems.PLAYERS_VISIBLE, "§aAlle Spieler anzeigen");
        lobbyItems.put(LobbyItems.COMPASS_BUILDFFA, "BuildFFA");
        lobbyItems.put(LobbyItems.INVENTORY_SORTER, "Inventar Sortierung");

        lobbyLocations.put(LobbyLocations.SPAWN, "Spawn");
        lobbyLocations.put(LobbyLocations.BUILD_FFA, "BuildFFA");
    }
}

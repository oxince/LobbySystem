package me.oxince.lobbysystem;

import me.oxince.lobbysystem.commands.BuildCommandExecutor;
import me.oxince.db.DatabaseController;
import me.oxince.lobbysystem.listener.*;
import me.oxince.lobbysystem.player.LobbyPlayer;
import me.oxince.locationmanager.LocationManager;
import me.oxince.locationmanager.SetLocationCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class LobbySystem extends JavaPlugin {
    private static LobbySystem lobbySystem;
    private static final String consolePrefix = "§8•§7● §6Lobby §8»";
    private static DatabaseController databaseController;
    private static LocationManager locationManager;
    private static HashMap<UUID, LobbyPlayer> lobbyPlayers = new HashMap<>();

    @Override
    public void onEnable() {
        lobbySystem = this;

        Config.initialize();

        databaseController = new DatabaseController(Config.mongoUrl, Config.databaseName, Config.databaseCollections);
        locationManager = new LocationManager(databaseController, "LobbySystem");

        Bukkit.getWorlds().forEach(world -> {
            world.setStorm(false);
            world.setThundering(false);
        });

        Bukkit.getPluginManager().registerEvents(new BlockInteractionListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new FoodLevelChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerConnectionListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new ItemStackInteractionListener(), this);
        Bukkit.getPluginManager().registerEvents(new ItemDropPickupListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractAtEntityListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerToggleFlightListener(), this);
        Bukkit.getPluginManager().registerEvents(new WeatherChangeListener(), this);

        getCommand("location").setExecutor(new SetLocationCommand(locationManager));
        getCommand("build").setExecutor(new BuildCommandExecutor());
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.kickPlayer("Server closed.");
        });
    }

    public static String formatMessage(String message, boolean isConsole, Object... args) {
        if (!isConsole) {
            return String.format(String.format("%s §7%s", consolePrefix, message), args);
        }
        return String.format(String.format("LOBBYSYSTEM: §7%s", message), args);
    }

    public static DatabaseController getDatabaseController() {
        return databaseController;
    }

    public static LocationManager getLocationManager() {
        return locationManager;
    }

    public static void setLobbyPlayers(HashMap<UUID, LobbyPlayer> lobbyPlayers) {
        LobbySystem.lobbyPlayers = lobbyPlayers;
    }

    public static HashMap<UUID, LobbyPlayer> getLobbyPlayers() {
        return lobbyPlayers;
    }

    public static LobbySystem getLobbySystem() {
        return lobbySystem;
    }

    public static String getConsolePrefix() {
        return consolePrefix;
    }
}
package me.oxince.locationmanager;

import me.oxince.db.DatabaseController;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;

public class LocationManager {
    private final String pluginName;
    private final DatabaseController databaseController;

    private final HashMap<String, Location> locationMap = new HashMap<>();
    private final String locationsCollection = "locations";

    public LocationManager(DatabaseController databaseController, String pluginName) {
        this.databaseController = databaseController;
        this.pluginName = pluginName;
    }

    public void createLocation(Location location, String locationName, String setBy) {
        Document document = new Document();
        document.append("name", locationName);
        document.append("plugin", this.pluginName);
        document.append("setBy", setBy);
        document.append("world", location.getWorld().getName());
        document.append("x", location.getX());
        document.append("y", location.getY());
        document.append("z", location.getZ());
        document.append("yaw", location.getYaw());
        document.append("pitch", location.getPitch());

        databaseController.getCollection(locationsCollection).insertOne(document);
        locationMap.put(String.format("%s_%s", pluginName, locationName), parseDocument(document));
    }

    public void removeLocation(String locationName) {
        Document document = databaseController.findFirst(locationsCollection, new Document().append("name", locationName).append("plugin", this.pluginName));
        databaseController.getCollection(locationsCollection).deleteOne(document);
        locationMap.remove(String.format("%s_%s", pluginName, locationName));
    }

    public Location getLocation(String locationName) {
        String mapKey = String.format("%s_%s", pluginName, locationName);

        if (locationMap.containsKey(mapKey)) {
            return locationMap.get(mapKey);
        }

        Document document = databaseController.findFirst(locationsCollection, new Document().append("name", locationName).append("plugin", this.pluginName));
        locationMap.put(String.format("%s_%s", pluginName, locationName), parseDocument(document));

        return parseDocument(document);
    }

    private Location parseDocument(Document document) {
        return new Location(Bukkit.getWorld(document.get("world").toString()), (double) document.get("x"), (double) document.get("y"), (double) document.get("z"), Float.parseFloat(document.get("yaw").toString()), Float.parseFloat(document.get("pitch").toString()));
    }
}

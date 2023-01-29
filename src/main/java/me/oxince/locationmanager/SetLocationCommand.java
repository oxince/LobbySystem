package me.oxince.locationmanager;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLocationCommand implements CommandExecutor {
    private final String commandPermission = "locationmanager.setlocation";
    private final LocationManager locationManager;

    public SetLocationCommand(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            return false;
        }

        if (args.length <= 1) {
            commandSender.sendMessage("§cInvalid usage: §7/location <remove, add, tp, center> <name, yaw>");
            return false;
        }

        if (!commandSender.hasPermission(commandPermission)) {
            return false;
        }

        Player player = (Player) commandSender;

        String interaction = args[0].toString();
        String name = args[1].toString();

        switch (interaction) {
            case "center":
                player.teleport(new Location(player.getWorld(), player.getLocation().getBlockX() + 0.5, player.getLocation().getBlockY() + 0.5, player.getLocation().getBlockZ() + 0.5, Float.parseFloat(args[1]), 0));
                break;
            case "add":
                player.sendMessage("§6Trying to set location.");
                try {
                    locationManager.createLocation(player.getLocation(), name, player.getName());
                    player.sendMessage("§aSuccessfully set location.");
                } catch (Exception ignored) {
                    player.sendMessage("§cSetting location failed.");
                }
                break;
            case "remove":
                player.sendMessage("§6Trying to remove location.");
                try {
                    locationManager.removeLocation(name);
                    player.sendMessage("§aSuccessfully removed location.");
                } catch (Exception ignored) {
                    player.sendMessage("§cRemoving location failed.");
                }
                break;
            case "tp":
                player.sendMessage("§6Teleporting to location.");
                try {
                    player.teleport(locationManager.getLocation(name));
                    player.sendMessage("§aSuccessfully teleported to location.");
                } catch (Exception ignored) {
                    player.sendMessage("§cFailed teleporting to location.");
                }
                break;
        }

        return false;
    }
}

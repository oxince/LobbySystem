package me.oxince.lobbysystem.listener;

import me.oxince.lobbysystem.Config;
import me.oxince.lobbysystem.LobbySystem;
import me.oxince.lobbysystem.enums.LobbyItems;
import me.oxince.lobbysystem.enums.LobbyLocations;
import me.oxince.lobbysystem.events.ItemStackInteractionEvent;
import me.oxince.lobbysystem.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class ItemStackInteractionListener implements Listener {
    @EventHandler
    public void onItemStackInteraction(ItemStackInteractionEvent itemStackInteractionEvent) {
        Player player = itemStackInteractionEvent.getPlayer();

        ItemStack itemStack = itemStackInteractionEvent.getItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();

        String originItemName = ItemBuilder.getOriginItemName(itemMeta.getDisplayName());
        HashMap<LobbyItems, String> lobbyItems = Config.lobbyItems;

//        player.sendMessage("[§6DEBUG§f]: " + originItemName);

        // Player-Inventory Items
        if (originItemName.equals(lobbyItems.get(LobbyItems.COMPASS))) {
            Inventory inventory = Bukkit.createInventory(null, 9, "Wähle einen Spielmodus");
            inventory.setItem(5 - 1, new ItemBuilder(Material.SANDSTONE).setName(Config.lobbyItems.get(LobbyItems.COMPASS_BUILDFFA), true).getItemStack());
            player.openInventory(inventory);
        } else if (originItemName.equals(lobbyItems.get(LobbyItems.LOBBY_SWITCHER))) {
            Inventory inventory = Bukkit.createInventory(null, 9, "Wähle eine Lobby");
            player.openInventory(inventory);
        } else if (originItemName.equals(lobbyItems.get(LobbyItems.COMPASS_BUILDFFA))) {
            teleportToGame(player, Config.lobbyLocations.get(LobbyLocations.BUILD_FFA));
        }
    }

    private void teleportToGame(Player player, String locationName) {
        Location location = getLocation(locationName);

        if (location != null) {
            player.teleport(location);
            player.playSound(location, Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
            player.sendMessage(LobbySystem.formatMessage("Du hast dich §aerfolgreich §7teleportiert.", false));
        } else {
            player.sendMessage(LobbySystem.formatMessage("Dieser Modus befindet sich aktuell in §cWartungsarbeiten§7.", false));
        }
    }

    private Location getLocation(String locationName) {
        try {
            return LobbySystem.getLocationManager().getLocation(locationName);
        } catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
        }
        return null;
    }
}

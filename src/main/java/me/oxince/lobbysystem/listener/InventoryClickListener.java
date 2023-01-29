package me.oxince.lobbysystem.listener;

import me.oxince.lobbysystem.LobbySystem;
import me.oxince.lobbysystem.events.ItemStackInteractionEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent inventoryClickListener) {
        Player player;

        if (inventoryClickListener.getWhoClicked() instanceof Player) {
            player = (Player) inventoryClickListener.getWhoClicked();
            if (!LobbySystem.getLobbyPlayers().get(player.getUniqueId()).isInBuildMode)
                inventoryClickListener.setCancelled(true);
        } else return;

        if (inventoryClickListener.getInventory().getHolder() instanceof Player)
            return;

        if (inventoryClickListener.getCurrentItem() == null || inventoryClickListener.getCurrentItem().getItemMeta() == null)
            return;

        Bukkit.getPluginManager().callEvent(new ItemStackInteractionEvent(player, inventoryClickListener.getCurrentItem()));
    }
}

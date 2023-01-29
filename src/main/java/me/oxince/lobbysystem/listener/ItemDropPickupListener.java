package me.oxince.lobbysystem.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class ItemDropPickupListener implements Listener {
    @EventHandler
    public void onInventoryDrop(PlayerDropItemEvent playerDropItemEvent) {
        playerDropItemEvent.setCancelled(true);
    }

    @EventHandler
    public void onPlayerPickup(PlayerPickupItemEvent playerPickupItemEvent) {
        playerPickupItemEvent.setCancelled(true);
    }
}

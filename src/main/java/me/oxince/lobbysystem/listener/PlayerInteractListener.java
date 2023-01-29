package me.oxince.lobbysystem.listener;

import me.oxince.lobbysystem.events.ItemStackInteractionEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent playerInteractListener) {
        ItemStack interactionItemStack = playerInteractListener.getItem();

        if (interactionItemStack == null || interactionItemStack.getType() == Material.AIR || !(playerInteractListener.getAction() == Action.RIGHT_CLICK_BLOCK || playerInteractListener.getAction() == Action.RIGHT_CLICK_AIR)) {
            return;
        }

        if (interactionItemStack.getItemMeta().getDisplayName() != null) {
            Bukkit.getPluginManager().callEvent(new ItemStackInteractionEvent(playerInteractListener.getPlayer(), interactionItemStack));
        }
    }
}

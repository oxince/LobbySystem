package me.oxince.lobbysystem.listener;

import me.oxince.lobbysystem.LobbySystem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockInteractionListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent blockBreakEvent) {
        if (!LobbySystem.getLobbyPlayers().get(blockBreakEvent.getPlayer().getUniqueId()).isInBuildMode) {
            blockBreakEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent blockPlaceEvent) {
        if (!LobbySystem.getLobbyPlayers().get(blockPlaceEvent.getPlayer().getUniqueId()).isInBuildMode) {
            blockPlaceEvent.setCancelled(true);
        }
    }
}

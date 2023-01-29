package me.oxince.lobbysystem.listener;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class PlayerToggleFlightListener implements Listener {
    public static HashMap<UUID, Boolean> cooldownMap = new HashMap<>();

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent playerToggleFlightEvent) {
        Player player = playerToggleFlightEvent.getPlayer();

        if (player.getGameMode() == GameMode.CREATIVE) return;
        playerToggleFlightEvent.setCancelled(true);

        if ((cooldownMap.get(player.getUniqueId()) == null || !cooldownMap.get(player.getUniqueId())) && player.getWorld().getBlockAt(player.getLocation().subtract(0,2,0)).getType() != Material.AIR) {
            cooldownMap.put(player.getUniqueId(), true);

            Vector vector = player.getLocation().getDirection().multiply(2).setY(3);
            player.playSound(player.getLocation(), Sound.FIREWORK_LAUNCH, 1.0F, 1.0F);
            player.setVelocity(vector);
            player.setAllowFlight(false);
        }
    }
}

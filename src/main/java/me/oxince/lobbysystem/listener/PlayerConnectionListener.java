package me.oxince.lobbysystem.listener;

import me.oxince.lobbysystem.LobbySystem;
import me.oxince.lobbysystem.player.LobbyPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlayerConnectionListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        LobbyPlayer lobbyPlayer = new LobbyPlayer(player);
        HashMap<UUID, LobbyPlayer> lobbyPlayers = LobbySystem.getLobbyPlayers();

        playerJoinEvent.setJoinMessage(null);
        lobbyPlayers.put(player.getUniqueId(), lobbyPlayer);
        lobbyPlayer.onJoin();

        LobbySystem.setLobbyPlayers(lobbyPlayers);
    }

    public void onPlayerQuit(PlayerQuitEvent playerQuitEvent) {
        playerQuitEvent.setQuitMessage(null);
        PlayerToggleFlightListener.cooldownMap.remove(playerQuitEvent.getPlayer().getUniqueId());
    }
}

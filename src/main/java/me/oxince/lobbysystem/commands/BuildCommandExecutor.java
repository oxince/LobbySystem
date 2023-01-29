package me.oxince.lobbysystem.commands;

import me.oxince.lobbysystem.LobbySystem;
import me.oxince.lobbysystem.player.LobbyPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class BuildCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            return false;
        } else if (!commandSender.hasPermission("lobby.build")) {
            return false;
        }

        Player player = (Player) commandSender;
        LobbyPlayer lobbyPlayer = LobbySystem.getLobbyPlayers().get(player.getUniqueId());

        if (lobbyPlayer != null) {
            lobbyPlayer.setBuildMode(!lobbyPlayer.isInBuildMode);
            player.sendMessage(LobbySystem.formatMessage("Build-Modus §aerfolgreich §7aktualisiert.", false));
        }

        return false;
    }
}

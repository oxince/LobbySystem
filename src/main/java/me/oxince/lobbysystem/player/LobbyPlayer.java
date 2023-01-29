package me.oxince.lobbysystem.player;

import me.oxince.lobbysystem.Config;
import me.oxince.lobbysystem.LobbySystem;
import me.oxince.lobbysystem.enums.LobbyItems;
import me.oxince.lobbysystem.enums.LobbyLocations;
import me.oxince.lobbysystem.utils.ItemBuilder;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LobbyPlayer {
    private final Player player;
    public boolean isInBuildMode = false;

    public LobbyPlayer(Player player) {
        this.player = player;
    }

    public void setBuildMode(boolean enabled) {
        isInBuildMode = enabled;

        if (isInBuildMode) {
            player.setGameMode(GameMode.CREATIVE);
            player.getInventory().clear();
        } else {
            player.setGameMode(GameMode.ADVENTURE);
            setDefaultInventory(player.getInventory());
        }
    }

    public void setDefaultInventory(PlayerInventory playerInventory) {
        ItemStack compassItemStack = new ItemBuilder(Material.COMPASS).setName(Config.lobbyItems.get(LobbyItems.COMPASS), true).getItemStack();
//        ItemStack profileItemStack = new ItemBuilder(Material.SKULL_ITEM, 1, (byte) 3).setName(Config.lobbyItems.get(LobbyItems.PROFILE), true).setSkullOwner(player.getName()).getItemStack();
//        ItemStack playerVisibilityItemStack = new ItemBuilder(Material.INK_SACK, 1, (byte) 10).setName(Config.lobbyItems.get(LobbyItems.PLAYERS_VISIBLE), true).getItemStack();
//        ItemStack lobbySwitcherItemStack = new ItemBuilder(Material.NETHER_STAR).setName(Config.lobbyItems.get(LobbyItems.LOBBY_SWITCHER), true).getItemStack();

        playerInventory.clear();
        playerInventory.setArmorContents(null);

//        playerInventory.setItem(1 - 1, playerVisibilityItemStack);
        playerInventory.setItem(5 - 1, compassItemStack);
//        playerInventory.setItem(6 - 1, lobbySwitcherItemStack);
//        playerInventory.setItem(9 - 1, profileItemStack);
    }

    public void onJoin() {
        PlayerInventory playerInventory = this.player.getInventory();
        String spawnLocation = Config.lobbyLocations.get(LobbyLocations.SPAWN);

        this.setDefaultInventory(playerInventory);
        player.setHealth(3.0 * 2);
        player.setMaxHealth(3.0 * 2);
        player.setFoodLevel(10 * 2);

        player.setAllowFlight(true);
        player.setGameMode(GameMode.ADVENTURE);

        try {
            CraftPlayer craftPlayer = (CraftPlayer) player;
            PacketPlayOutWorldParticles packetPlayOutWorldParticles = new PacketPlayOutWorldParticles(EnumParticle.MOB_APPEARANCE, false, 0, 0, 0, 0, 0, 0, 0, 1);
            PotionEffect potionEffect = new PotionEffect(PotionEffectType.BLINDNESS, 45, 10);

            player.teleport(LobbySystem.getLocationManager().getLocation(spawnLocation));
            player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 2.0F, 2.0F);
            player.removePotionEffect(potionEffect.getType());
            player.addPotionEffect(potionEffect);
            craftPlayer.getHandle().playerConnection.sendPacket(packetPlayOutWorldParticles);
        } catch (NullPointerException exception) {
            Bukkit.getConsoleSender().sendMessage(LobbySystem.formatMessage("§cERROR: §fLocation %s does not exists.", true, spawnLocation));
        }
    }
}

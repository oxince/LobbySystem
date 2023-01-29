package me.oxince.lobbysystem.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageEvent entityDamageEvent) {
        if (entityDamageEvent.getEntityType() == EntityType.PLAYER) {
            entityDamageEvent.setCancelled(true);

            if (entityDamageEvent.getCause() == EntityDamageEvent.DamageCause.FALL) {
                Player player = (Player) entityDamageEvent.getEntity();

                PlayerToggleFlightListener.cooldownMap.put(player.getUniqueId(), false);
                player.setAllowFlight(true);
            }
        }
    }
}

package me.oxince.lobbysystem.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeListener implements Listener {
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent weatherChangeEvent) {
        weatherChangeEvent.setCancelled(true);
    }
}

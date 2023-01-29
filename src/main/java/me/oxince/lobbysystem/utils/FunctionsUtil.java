package me.oxince.lobbysystem.utils;

import org.bukkit.Location;

public class FunctionsUtil {
    public static double getDistanceBetweenLocations(Location pointA, Location pointB) {
        double dx = pointB.getX() - pointA.getX();
        double dy = pointB.getY() - pointA.getY();
        double dz = pointB.getZ() - pointA.getZ();

        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2) + Math.pow(dz, 2));
    }
}

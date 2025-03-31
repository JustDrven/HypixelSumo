package dev.justdrven.sumo.player;

import org.bukkit.Location;

import java.util.UUID;

public class PlayerTeleportData {

    public final UUID playerUUID;
    public final Location location;

    public PlayerTeleportData(UUID playerUUID, Location location) {
        this.playerUUID = playerUUID;
        this.location = location;
    }
}

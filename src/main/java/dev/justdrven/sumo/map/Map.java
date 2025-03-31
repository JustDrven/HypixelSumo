package dev.justdrven.sumo.map;

import org.bukkit.Location;

public class Map {

    private final String mapName;
    private final String worldName;
    private final String mode;
    private final String version;
    private final String authors;

    private final Location redLocation;
    private final Location blueLocation;
    private final Location spectatorsLocation;

    public Map(String mapName, String worldName, String mode, String version, String authors, Location redLocation, Location blueLocation, Location spectatorsLocation) {
        this.mapName = mapName;
        this.worldName = worldName;
        this.mode = mode;
        this.version = version;
        this.authors = authors;
        this.redLocation = redLocation;
        this.blueLocation = blueLocation;
        this.spectatorsLocation = spectatorsLocation;
    }

    public String getMapName() {
        return mapName;
    }

    public String getWorldName() {
        return worldName;
    }

    public String getMode() {
        return mode;
    }

    public String getVersion() {
        return version;
    }

    public String getAuthors() {
        return authors;
    }

    public Location getRedLocation() {
        return redLocation;
    }

    public Location getBlueLocation() {
        return blueLocation;
    }

    public Location getSpectatorsLocation() {
        return spectatorsLocation;
    }
}

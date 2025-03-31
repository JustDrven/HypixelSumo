package dev.justdrven.sumo.map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class MapConfig {
    private JsonObject settings;
    private final String MAP_CONFIG_FILE = "mapConfig.json";
    private final File mapConfigFile = new File(MAP_CONFIG_FILE);

    public MapConfig() {
        try {
            if (mapConfigFile.createNewFile()) {
                System.out.println(MAP_CONFIG_FILE + " file was created!");
            }

            this.settings = new JsonParser().parse(new FileReader(mapConfigFile)).getAsJsonObject();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public final Map loadMap() {
        String mapName = settings.get("mapName").getAsString();
        String worldName = settings.get("worldName").getAsString();
        String mode = settings.get("mode").getAsString();
        String version = settings.get("version").getAsString();
        String authors = settings.get("authors").getAsString();

        Location spectators = generateLocation(worldName, "spectators");
        Location red = generateLocation(worldName, "red");
        Location blue = generateLocation(worldName, "blue");

        return new Map(
                mapName,
                worldName,
                mode,
                version,
                authors,
                red,
                blue,
                spectators
        );
    }

    private Location generateLocation(String worldName, String locationId) {
        JsonObject locations = settings.get("locations").getAsJsonObject().get(locationId).getAsJsonObject();

        Location location = new Location(
                Bukkit.getWorld(worldName),
                locations.get("x").getAsDouble(),
                locations.get("y").getAsDouble(),
                locations.get("z").getAsDouble()
        );

        location.setYaw(locations.get("yaw").getAsFloat());
        location.setPitch(locations.get("pitch").getAsFloat());

        return location;
    }

    public final void save() {
        try (FileWriter fileWriter = new FileWriter(mapConfigFile)) {
            String jsonData = settings.toString();

            fileWriter.write(jsonData);
        } catch (Exception ignore){}
    }

    public final JsonObject getSettings() {
        return settings;
    }
}

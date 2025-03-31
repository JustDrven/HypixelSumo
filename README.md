# Hypixel Sumo

This plugin is only inspired by **Hypixel's sumo**. The plugin is for 1.8 spigot version.
This plugin is only for my portfolio. <br>
The minigame is not intended for the production side.

# Requirements

 - Java 8
 - Spigot server on 1.8 version

# How To Build

- Install gradle build tool [here](https://gradle.org).
- Type `./gradlew shadowJar shadowJar` in terminal.

### Map Config

The minigame require 'mapConfig.json' file. <br>
Here is example file.

```json
{
  "mapName": "Test Map",
  "authors": "Hypixel Builders",
  "worldName": "sumo_map",
  "mode": "Sumo Duel",
  "version": "1.4",
  "locations": {
    "spectators": {
      "x": 0.0,
      "y": 0.0,
      "z": 0.0,
      "yaw": 0.0,
      "pitch": 0.0
    },
    "red": {
      "x": 0.0,
      "y": 0.0,
      "z": 0.0,
      "yaw": 0.0,
      "pitch": 0.0
    },
    "blue": {
      "x": 0.0,
      "y": 0.0,
      "z": 0.0,
      "yaw": 0.0,
      "pitch": 0.0
    }
  }
}
```

---

Developed by: **JustDrven**
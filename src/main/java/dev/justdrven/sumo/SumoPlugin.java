package dev.justdrven.sumo;

import dev.justdrven.sumo.commands.SetLocationCommand;
import dev.justdrven.sumo.listener.DisableListener;
import dev.justdrven.sumo.listener.GameListener;
import dev.justdrven.sumo.listener.PlayerListener;
import dev.justdrven.sumo.listener.ServerListener;
import dev.justdrven.sumo.manager.TeleportManager;
import dev.justdrven.sumo.map.Map;
import dev.justdrven.sumo.map.MapConfig;

import dev.justdrven.sumo.task.WaitingTask;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SumoPlugin extends JavaPlugin {

    private static Map map;
    private static TeleportManager teleportManager;
    private static MapConfig mapConfig;
    private static Game game;
    private static WaitingTask waitingTask;

    @Override
    public void onEnable() {
        teleportManager = new TeleportManager();
        mapConfig = new MapConfig();
        map = mapConfig.loadMap();

        System.out.println("The map is loaded!");

        game = new Game(this);

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new DisableListener(game), this);
        pm.registerEvents(new PlayerListener(game), this);
        pm.registerEvents(new GameListener(), this);
        pm.registerEvents(new ServerListener(game), this);

        getCommand("setloc").setExecutor(new SetLocationCommand());

        waitingTask = new WaitingTask(game);
        runWaitingTimer();


        System.out.println("The game is ready!");
    }

    @Override
    public void onDisable() {
        getTeleportManager().close();
        waitingTask.cancel();
    }

    public Game getGame() {
        return game;
    }

    public static Map getMap() {
        return map;
    }

    public static MapConfig getMapConfig() {
        return mapConfig;
    }

    public static void setGame(Game game) {
        SumoPlugin.game = game;
    }

    public static TeleportManager getTeleportManager() {
        return teleportManager;
    }

    public static void runWaitingTimer() {
        waitingTask.runTaskTimer(game.getPlugin(), 0L, 20L);
    }

}

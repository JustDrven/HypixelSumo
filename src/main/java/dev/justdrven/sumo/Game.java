package dev.justdrven.sumo;

import dev.justdrven.sumo.item.ItemManager;
import dev.justdrven.sumo.item.type.LobbyItemContainer;
import dev.justdrven.sumo.item.type.SpectatorItemContainer;
import dev.justdrven.sumo.event.StateChangeEvent;
import dev.justdrven.sumo.manager.PlayersManager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.atomic.AtomicInteger;

public class Game {
    
    private final Plugin plugin;
    private final PlayersManager playersManager;
    private final ItemManager itemManager;
    private GameStatus status = GameStatus.WAITING;
    private final AtomicInteger timeLeft = new AtomicInteger(10);

    public Game(Plugin plugin) {
        this.plugin = plugin;
        this.playersManager = new PlayersManager(this);
        this.itemManager = new ItemManager();

        registerItemContainers();
    }

    public boolean isLobby() {
        return isStatus(GameStatus.WAITING) ||
                isStatus(GameStatus.STARTING);
    }

    public final boolean isInGame() {
        return !isLobby();
    }

    public final void setStatus(GameStatus status) {
        StateChangeEvent event = new StateChangeEvent(this.status, status, this);
        Bukkit.getPluginManager().callEvent(event);

        this.status = status;
    }

    public final GameStatus getStatus() {
        return status;
    }

    public final boolean isStatus(GameStatus status) {
        return getStatus().equals(status);
    }

    public final PlayersManager getPlayersManager() {
        return playersManager;
    }

    public final ItemManager getItemManager() {
        return itemManager;
    }

    private void registerItemContainers() {
        getItemManager().registerItemContainer("spectators", new SpectatorItemContainer());
        getItemManager().registerItemContainer("lobby", new LobbyItemContainer());
    }

    public final void broadcast(final String message) {
        Bukkit.broadcastMessage(message);
    }

    public final AtomicInteger getTimeLeft() {
        return timeLeft;
    }

    public final Plugin getPlugin() {
        return plugin;
    }
}

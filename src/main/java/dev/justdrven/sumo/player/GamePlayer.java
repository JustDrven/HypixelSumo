package dev.justdrven.sumo.player;

import dev.justdrven.sumo.Game;
import dev.justdrven.sumo.SumoPlugin;
import dev.justdrven.sumo.map.Map;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

import java.util.UUID;

public class GamePlayer {

    private final UUID uuid;
    private final Game game;
    private boolean spectator = false;

    public GamePlayer(Game game, UUID uuid) {
        this.game = game;
        this.uuid = uuid;
    }

    public final String getNick() {
        return getOfflinePlayer().getName();
    }

    public final UUID getUuid() {
        return uuid;
    }

    public final OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(uuid);
    }

    public final boolean isOnline() {
        return getOfflinePlayer().isOnline();
    }

    public final Player getPlayer() {
        return getOfflinePlayer().getPlayer();
    }

    public final void sendTitle(String title, String subtitle) {
        getPlayer().sendTitle(title, subtitle);
    }

    public final void sendTitle(String title) {
        sendTitle(title, "§c§l ");
    }

    public final void sendSubTitle(String subtitle) {
        sendTitle("§c§l ", subtitle);
    }

    public final void sendMessage(final String message) {
        if (!isOnline()) return;

        getPlayer().sendMessage(message);
    }

    public final boolean isSpectator() {
        return spectator;
    }

    public final void setSpectator() {
        if (!isSpectator())
            this.spectator = true;

        clear();
        getGame().getItemManager().get("spectators").giveItems(this, getInventory());

        Player bukkitPlayer = getPlayer();

        bukkitPlayer.setGameMode(GameMode.ADVENTURE);
        bukkitPlayer.setFoodLevel(20);
        bukkitPlayer.setHealth(bukkitPlayer.getMaxHealth());
        bukkitPlayer.setAllowFlight(true);

        Map map = SumoPlugin.getMap();
        if (map != null) {
            getPlayer().teleport(map.getSpectatorsLocation());
        }

        Bukkit.getOnlinePlayers().forEach(otherPlayer -> {
            if (!otherPlayer.equals(bukkitPlayer)) {
                otherPlayer.hidePlayer(bukkitPlayer);
            }
        });

    }

    public final Game getGame() {
        return game;
    }

    public final Inventory getInventory() {
        return getPlayer().getInventory();
    }

    public final void clear() {
        PlayerInventory inventory = getPlayer().getInventory();

        inventory.clear();
        inventory.setArmorContents(null);

        getPlayer().setHealth(getPlayer().getMaxHealth());
        getPlayer().setFoodLevel(20);
    }

}

package dev.justdrven.sumo.manager;

import dev.justdrven.sumo.player.PlayerTeleportData;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TeleportManager {

    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final List<PlayerTeleportData> teleportDataList = new ArrayList<>();
    private final Lock lock = new ReentrantLock();

    public final void teleport(Player player, Location location) {
        teleportDataList.add(
                new PlayerTeleportData(player.getUniqueId(), location)
        );
    }

    public final void remove(Player player) {
        teleportDataList.removeIf(teleportData -> teleportData.playerUUID.equals(player.getUniqueId()));
    }

    public final void complete() {
        executorService.submit(() -> {
            lock.lock();
            try {

                for (PlayerTeleportData playerTeleportData : teleportDataList) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerTeleportData.playerUUID);

                    if (!offlinePlayer.isOnline()) continue;

                    offlinePlayer.getPlayer().teleport(playerTeleportData.location);
                }

                teleportDataList.clear();
            } finally {
                lock.unlock();
            }
        });
    }

    public final void close() {
        executorService.shutdown();
    }

}

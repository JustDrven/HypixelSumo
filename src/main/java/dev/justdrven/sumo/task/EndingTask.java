package dev.justdrven.sumo.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class EndingTask extends BukkitRunnable {

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player ->
                player.kickPlayer("§cGame is restarting.."));

        Bukkit.shutdown();
    }

}

package dev.justdrven.sumo.listener;

import dev.justdrven.sumo.Game;
import dev.justdrven.sumo.SumoPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.ArrayList;
import java.util.List;

public class ServerListener implements Listener {

    private final Game game;

    public ServerListener(Game game) {
        this.game = game;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPing(ServerListPingEvent e) {
        e.setMotd(status());
    }

    private String status() {
        List<String> lore = new ArrayList<>();
        lore.add("§fPlayers: §a" + game.getPlayersManager().getAlivePlayers().size() + "/2");

        if (game.isLobby()) {
            lore.add("§fMap: §eVoting..");
        } else {
            lore.add("§fMap: §e" + SumoPlugin.getMap().getMapName());
        }

        lore.add("§fMode: §e" + SumoPlugin.getMap().getMode());
        lore.add("§fAuthors: §a" + SumoPlugin.getMap().getAuthors());
        lore.add("§fVersion: §7v" + SumoPlugin.getMap().getVersion());
        if (game.isInGame()) {
            lore.add("§cInGame");
        }


        return String.join("§r|", lore);
    }



}

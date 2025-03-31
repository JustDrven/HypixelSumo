package dev.justdrven.sumo.listener;

import dev.justdrven.sumo.Game;
import dev.justdrven.sumo.GameStatus;
import dev.justdrven.sumo.SumoPlugin;
import dev.justdrven.sumo.event.GameWinEvent;
import dev.justdrven.sumo.event.StateChangeEvent;
import dev.justdrven.sumo.player.GamePlayer;
import dev.justdrven.sumo.scoreboard.BoardManager;
import dev.justdrven.sumo.task.EndingTask;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class GameListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onStateChange(StateChangeEvent e) {
        if (e.getFrom().equals(GameStatus.STARTING)) {
            Game game = e.getGame();

            game.getPlayersManager().getPlayers().forEach(gamePlayer -> {
                if (!gamePlayer.isSpectator()) {
                    gamePlayer.clear();
                    gamePlayer.getPlayer().playSound(gamePlayer.getPlayer().getEyeLocation(), Sound.LEVEL_UP, 1.5f, 1.5f);

                } else {
                    SumoPlugin.getTeleportManager().teleport(gamePlayer.getPlayer(), SumoPlugin.getMap().getSpectatorsLocation());
                }

                BoardManager.createInGame(gamePlayer);
            });

            game.broadcast("§aGame started!");
            SumoPlugin.getTeleportManager().complete();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onGameWin(GameWinEvent e) {
        SumoPlugin.getTeleportManager().complete();

        if (e.getWinner() != null) {
            final String winnerName = e.getWinner().getNick();
            e.getGame().getPlayersManager().getPlayers().stream()
                    .filter(gamePlayer -> !gamePlayer.equals(e.getWinner()))
                    .forEach(gamePlayer -> {
                        gamePlayer.sendTitle("§c§lDEFEAT!", "§6" + winnerName + " §fwon the Duel!");

                        Player otherPlayer = gamePlayer.getPlayer();
                        otherPlayer.playSound(otherPlayer.getEyeLocation(), Sound.VILLAGER_DEATH, 0.5f, 1.0f);
                    });

            e.getWinner().sendTitle("§e§lVICTORY", "§6" + winnerName + " §fwon the Duel!");
            Player winnerPlayer = e.getWinner().getPlayer();
            winnerPlayer.playSound(winnerPlayer.getEyeLocation(), Sound.ENDERMAN_DEATH, 1.3f, 1.5f);
        } else {
            e.getGame().getPlayersManager().getPlayers().forEach(gamePlayer -> {
                gamePlayer.sendTitle("§c§lDRAW!", "§fNobody won!");

                Player otherPlayer = gamePlayer.getPlayer();
                otherPlayer.playSound(otherPlayer.getEyeLocation(), Sound.VILLAGER_DEATH, 1.0f, 1.0f);
            });
        }

        e.getGame().broadcast("§eGame will be stopped at §c10s");
        new EndingTask().runTaskLater(e.getGame().getPlugin(), 20 * 10L);

    }

}

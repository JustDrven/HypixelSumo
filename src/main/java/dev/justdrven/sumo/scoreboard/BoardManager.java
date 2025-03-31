package dev.justdrven.sumo.scoreboard;

import dev.justdrven.sumo.Game;
import dev.justdrven.sumo.GameStatus;
import dev.justdrven.sumo.SumoPlugin;
import dev.justdrven.sumo.player.GamePlayer;
import dev.justdrven.sumo.task.WaitingTask;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class BoardManager {

    public static void createLobby(GamePlayer player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("Lobby", "dummy");

        obj.setDisplayName("§e§lDUELS");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        obj.getScore("§a").setScore(8);
        obj.getScore("§fPlayers: §a" + player.getGame().getPlayersManager().getAlivePlayers().size() + "/2").setScore(7);
        obj.getScore("§c").setScore(6);
        if (player.getGame().isStatus(GameStatus.WAITING)) {
            obj.getScore("§fWaiting...").setScore(5);
        } else {
            obj.getScore("§fStarting in §a" + WaitingTask.getTimeLeft().get() + "s").setScore(5);
        }
        obj.getScore("§1").setScore(4);
        obj.getScore("§fMode: §a" + SumoPlugin.getMap().getMode()).setScore(3);
        obj.getScore("§fVersion: §7v" + SumoPlugin.getMap().getVersion()).setScore(2);
        obj.getScore("§2").setScore(1);
        obj.getScore("§ewww.hypixel.net").setScore(0);

        player.getPlayer().setScoreboard(board);
    }

    public static void createInGame(GamePlayer player) {
        Game g = player.getGame();

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("InGame", "dummy");

        obj.setDisplayName("§e§lDUELS");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        obj.getScore("§a").setScore(7);
        obj.getScore("§f§lOpponent:").setScore(6);
        obj.getScore(getOpponentName(player)).setScore(5);
        obj.getScore("§f").setScore(4);
        obj.getScore("§fMode: §a" + SumoPlugin.getMap().getMode()).setScore(3);
        obj.getScore("§fVersion: §7v" + SumoPlugin.getMap().getVersion()).setScore(2);
        obj.getScore("§c").setScore(1);
        obj.getScore("§ewww.hypixel.net").setScore(0);

        player.getPlayer().setScoreboard(board);
    }

    private static String getOpponentName(GamePlayer p) {
        if (p.isSpectator()) {
            return "§cYou're spectator";
        }

        Game g = p.getGame();

        return "§a" + g.getPlayersManager().getAlivePlayers().stream()
                .filter(gamePlayer -> !gamePlayer.equals(p))
                .map(GamePlayer::getNick)
                .findFirst()
                .orElse("§cUnknown");

    }

}

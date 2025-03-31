package dev.justdrven.sumo.task;

import dev.justdrven.sumo.Game;
import dev.justdrven.sumo.GameStatus;
import dev.justdrven.sumo.SumoPlugin;
import dev.justdrven.sumo.manager.TeleportManager;
import dev.justdrven.sumo.map.Map;
import dev.justdrven.sumo.player.GamePlayer;
import dev.justdrven.sumo.scoreboard.BoardManager;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class WaitingTask extends BukkitRunnable {

    private final Game game;
    private static final int START_TIME = 10;
    private static final AtomicInteger timeLeft = new AtomicInteger(START_TIME);

    public WaitingTask(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        Supplier<Boolean> checkPlayersSize = () -> game.getPlayersManager().getAlivePlayers().size() >= 2;

        if (game.isStatus(GameStatus.WAITING)) {
            if (checkPlayersSize.get()) {
                game.setStatus(GameStatus.STARTING);
            }

            return;
        }
        if (game.isStatus(GameStatus.STARTING)) {
            if (!checkPlayersSize.get()) {
                resetGame();
                return;
            }

            if (timeLeft.get() == 0) {
                game.setStatus(GameStatus.INGAME);
                cancel();
                return;
            }

            sendTime();
            timeLeft.decrementAndGet();

        }
    }

    private void resetGame() {
        game.setStatus(GameStatus.WAITING);
        timeLeft.set(START_TIME);
    }

    private void sendTime() {
        boolean canSend = false;
        boolean last = false;

        switch (timeLeft.get()) {
            case 10:
            case 5:
            case 4:
            case 3:
            case 2:
                setGameLocations();

                canSend = true;
                break;
            case 1:
                canSend = true;
                last = true;
                break;
        }

        game.getPlayersManager().getPlayers().forEach(BoardManager::createLobby);

        if (canSend) {
            game.broadcast("§eThe game starts in §c" + timeLeft.get() + "§e " + (last ? "second" : "seconds") + "!");

            game.getPlayersManager().getPlayers().forEach(gamePlayer -> {
                Player bukkitPlayer = gamePlayer.getPlayer();

                bukkitPlayer.sendTitle("§c" + timeLeft.get(), "§a§l ");
                bukkitPlayer.playSound(bukkitPlayer.getEyeLocation(), Sound.NOTE_PLING, 0.9f, 0.1f);
            });
        }

    }

    public void setGameLocations() {
        List<GamePlayer> alivePlayersInList = new ArrayList<>(game.getPlayersManager().getAlivePlayers());
        TeleportManager teleportManager = SumoPlugin.getTeleportManager();

        GamePlayer firstPlayer = alivePlayersInList.get(0);
        GamePlayer secondPlayer = alivePlayersInList.get(1);


        Map map = SumoPlugin.getMap();
        if (map == null) return;

        teleportManager.teleport(firstPlayer.getPlayer(), map.getBlueLocation());
        teleportManager.teleport(secondPlayer.getPlayer(), map.getRedLocation());

    }

    public static AtomicInteger getTimeLeft() {
        return timeLeft;
    }
}

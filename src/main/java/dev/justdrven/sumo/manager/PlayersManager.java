package dev.justdrven.sumo.manager;

import dev.justdrven.sumo.Game;
import dev.justdrven.sumo.GameStatus;
import dev.justdrven.sumo.SumoPlugin;
import dev.justdrven.sumo.player.GamePlayer;
import dev.justdrven.sumo.player.KickType;
import dev.justdrven.sumo.scoreboard.BoardManager;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class PlayersManager {

    private final Game game;
    private final Set<GamePlayer> players = new LinkedHashSet<>();

    public PlayersManager(Game game) {
        this.game = game;
    }

    public final Optional<GamePlayer> getGamePlayer(Player player) {
        return players.stream()
                .filter(gamePlayer -> gamePlayer.getPlayer().equals(player))
                .findFirst();
    }

    public final Set<GamePlayer> getPlayers() {
        return players;
    }

    public final KickType canPlayerLogin(Player player) {
        if (getGame().isStatus(GameStatus.STOPPED)) {
            return KickType.GAME_STOPPED;
        }

        if (Bukkit.getOnlinePlayers().size() >= Bukkit.getMaxPlayers()) {
            return KickType.SERVER_FULL;
        }

        if (game.isInGame()) {
            if (!player.hasPermission("sumo.can.spectate")) {
                return KickType.GAME_STARTED;
            }
        }

        return KickType.PASSED;
    }


    public final Game getGame() {
        return game;
    }

    public final void joinPlayer(GamePlayer gamePlayer) {
        this.players.add(gamePlayer);

        gamePlayer.clear();

        gamePlayer.getPlayer().setGameMode(GameMode.SURVIVAL);
        gamePlayer.sendMessage("");
        if (game.isLobby()) {
            BoardManager.createLobby(gamePlayer);

            gamePlayer.getPlayer().teleport(SumoPlugin.getMap().getSpectatorsLocation());
            game.getItemManager().get("lobby").giveItems(gamePlayer, gamePlayer.getInventory());

            game.broadcast("§7" + gamePlayer.getNick() + " §ehas joined (§b" + getAlivePlayers().size() + "§7/§b2§e)!");

        } else {
            gamePlayer.setSpectator();

            BoardManager.createInGame(gamePlayer);
        }
    }

    public void quitPlayer(GamePlayer gamePlayer) {
        this.players.remove(gamePlayer);
        gamePlayer.clear();

        SumoPlugin.getTeleportManager().remove(gamePlayer.getPlayer());

        if (game.isLobby()) {
            game.broadcast("§7" + gamePlayer.getNick() + " §eleft the game (§b" + getAlivePlayers().size() + "§7/§b2§e)!");
        }
    }

    public Set<GamePlayer> getAlivePlayers() {
        return players.stream()
                .filter(gamePlayer -> !gamePlayer.isSpectator())
                .collect(Collectors.toSet());
    }

    public Set<GamePlayer> getSpecPlayers() {
        return players.stream()
                .filter(GamePlayer::isSpectator)
                .collect(Collectors.toSet());
    }

}

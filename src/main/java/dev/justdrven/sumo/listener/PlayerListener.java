package dev.justdrven.sumo.listener;

import dev.justdrven.sumo.Game;
import dev.justdrven.sumo.GameStatus;
import dev.justdrven.sumo.SumoPlugin;
import dev.justdrven.sumo.event.GameWinEvent;
import dev.justdrven.sumo.map.Map;
import dev.justdrven.sumo.player.GamePlayer;
import dev.justdrven.sumo.player.KickType;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Optional;

public class PlayerListener implements Listener {

    private final Game game;

    public PlayerListener(Game game) {
        this.game = game;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(PlayerLoginEvent e) {
        KickType kickType = game.getPlayersManager().canPlayerLogin(e.getPlayer());

        if (!kickType.equals(KickType.PASSED)) {
            e.disallow(kickType.getResult(), kickType.getReason());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);

        game.getPlayersManager().joinPlayer(
                new GamePlayer(game, e.getPlayer().getUniqueId())
        );
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);

        Optional<GamePlayer> gamePlayer = game.getPlayersManager().getGamePlayer(e.getPlayer());

        gamePlayer.ifPresent((gp ->
                game.getPlayersManager().quitPlayer(gp)));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFall(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;


        if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
            e.setCancelled(true);

            final Player p = ((Player)e.getEntity());
            if (game.isInGame()) {
                game.getPlayersManager().getGamePlayer(p).ifPresent(GamePlayer::setSpectator);

                if (canGameWin()) {
                    GamePlayer winner = game.getPlayersManager().getAlivePlayers().stream()
                            .filter(gamePlayer -> !gamePlayer.isSpectator())
                            .findFirst()
                            .orElse(null);

                    GameWinEvent event = new GameWinEvent(game, winner);
                    Bukkit.getPluginManager().callEvent(event);

                    game.setStatus(GameStatus.STOPPED);
                }

            }

            return;
        }

        e.setDamage(0);
    }

    private boolean canGameWin() {
        return game.getPlayersManager().getAlivePlayers().size() == 1;
    }

}

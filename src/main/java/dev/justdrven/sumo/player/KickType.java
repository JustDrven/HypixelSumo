package dev.justdrven.sumo.player;

import org.bukkit.event.player.PlayerLoginEvent;

public enum KickType {

    SERVER_FULL("§cServer is full!", PlayerLoginEvent.Result.KICK_FULL),
    GAME_STARTED("§cThat game already started!", PlayerLoginEvent.Result.KICK_OTHER),
    GAME_STOPPED("§cThe game is restarting..", PlayerLoginEvent.Result.KICK_OTHER),
    PASSED("", null);

    private final String reason;
    private final PlayerLoginEvent.Result result;

    KickType(String reason, PlayerLoginEvent.Result result) {
        this.reason = reason;
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public PlayerLoginEvent.Result getResult() {
        return result;
    }
}

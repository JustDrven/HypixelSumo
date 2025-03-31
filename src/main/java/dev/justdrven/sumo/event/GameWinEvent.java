package dev.justdrven.sumo.event;

import dev.justdrven.sumo.Game;
import dev.justdrven.sumo.player.GamePlayer;

public class GameWinEvent extends GameEvent {

    private final Game game;
    private final GamePlayer winner;

    public GameWinEvent(Game game, GamePlayer winner) {
        this.game = game;
        this.winner = winner;
    }

    public Game getGame() {
        return game;
    }

    public GamePlayer getWinner() {
        return winner;
    }
}

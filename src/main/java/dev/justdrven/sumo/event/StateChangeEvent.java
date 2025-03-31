package dev.justdrven.sumo.event;

import dev.justdrven.sumo.Game;
import dev.justdrven.sumo.GameStatus;

public class StateChangeEvent extends GameEvent {

    private final GameStatus from;
    private final GameStatus to;
    private final Game game;

    public StateChangeEvent(GameStatus from, GameStatus to, Game game) {
        this.from = from;
        this.to = to;
        this.game = game;
    }

    public GameStatus getFrom() {
        return from;
    }

    public GameStatus getTo() {
        return to;
    }

    public Game getGame() {
        return game;
    }

}

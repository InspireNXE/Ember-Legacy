package com.obsidianbox.ember.event;

import com.flowpowered.events.SimpleEvent;
import com.obsidianbox.ember.Game;

public abstract class GameEvent extends SimpleEvent {
    public final Game game;

    public GameEvent(Game game) {
        this.game = game;
    }

    /**
     * Fired when the {@link com.obsidianbox.ember.Game} starts.
     */
    public static class GameStartEvent extends GameEvent {
        public GameStartEvent(Game game) {
            super(game);
        }
    }

    /**
     * Fires when the {@link com.obsidianbox.ember.Game} stops.
     */
    public static class GameStopEvent extends GameEvent {
        public GameStopEvent(Game game) {
            super(game);
        }
    }
}

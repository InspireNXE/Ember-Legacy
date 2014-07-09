package com.obsidianbox.ember;

import com.flowpowered.events.EventHandler;
import com.flowpowered.events.Listener;
import com.flowpowered.events.Order;
import com.obsidianbox.ember.event.GameEvent;

public class GameListener implements Listener {
    private final Game game;

    public GameListener(Game game) {
        this.game = game;
    }

    @EventHandler(order = Order.EARLIEST)
    public void onGameStart(GameEvent.GameStartEvent event) {
        game.logger.info("GameStartEvent fired!");
    }
}

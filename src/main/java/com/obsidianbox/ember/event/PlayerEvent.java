package com.obsidianbox.ember.event;

import com.obsidianbox.ember.Game;
import com.obsidianbox.ember.physics.Player;

public abstract class PlayerEvent extends EntityEvent {
    public PlayerEvent(Game game, Player player) {
        super(game, player);
    }
}

package org.obsidianbox.ember.event;

import org.obsidianbox.ember.Game;
import org.obsidianbox.ember.physics.Player;

public abstract class PlayerEvent extends EntityEvent {
    public PlayerEvent(Game game, Player player) {
        super(game, player);
    }
}

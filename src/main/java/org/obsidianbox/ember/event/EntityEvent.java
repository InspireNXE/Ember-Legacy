package org.obsidianbox.ember.event;

import org.obsidianbox.ember.Game;
import org.obsidianbox.ember.physics.Entity;

public abstract class EntityEvent extends GameEvent {
    public final Entity entity;

    public EntityEvent(Game game, Entity entity) {
        super(game);
        this.entity = entity;
    }
}

package com.obsidianbox.ember.event;

import com.obsidianbox.ember.Game;
import com.obsidianbox.ember.physics.Entity;

public class EntityEvent extends GameEvent {
    public final Entity entity;

    public EntityEvent(Game game, Entity entity) {
        super(game);
        this.entity = entity;
    }
}

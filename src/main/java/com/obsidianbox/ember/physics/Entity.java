package com.obsidianbox.ember.physics;

import com.obsidianbox.ember.Game;
import com.obsidianbox.ember.physics.util.Transform;

import java.util.UUID;

public class Entity {
    protected final Game game;
    protected final UUID uuid;
    protected final int id;
    protected Transform transform;
    protected boolean isSavable = false;

    public Entity(Game game, int id, Transform transform) {
        this.game = game;
        uuid = UUID.randomUUID();
        this.id = id;
        this.transform = transform;
    }

    protected Entity(Game game, UUID uuid, int id, Transform transform) {
        this.game = game;
        this.uuid = uuid;
        this.id = id;
        this.transform = transform;
    }

    public Game getGame() {
        return game;
    }

    public UUID getUUID() {
        return uuid;
    }

    protected int getID() {
        return id;
    }

    public Transform getTransform() {
        return transform;
    }

    public boolean isSavable() {
        return isSavable;
    }

    public void setSavable(boolean isSavable) {
        this.isSavable = isSavable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity)) return false;

        Entity entity = (Entity) o;
        return uuid.equals(entity.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return "Entity{" +
                "uuid=" + uuid +
                ", id=" + id +
                ", transform=" + transform +
                ", isSavable=" + isSavable +
                '}';
    }
}

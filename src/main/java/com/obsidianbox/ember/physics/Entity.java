package com.obsidianbox.ember.physics;

import com.obsidianbox.ember.physics.util.Transform;

import java.util.UUID;

public class Entity {
    private final UUID uuid;
    private final int id;
    private Transform transform;
    private boolean isSavable = false;

    public Entity(int id, Transform transform) {
        uuid = UUID.randomUUID();
        this.id = id;
        this.transform = transform;
    }

    protected Entity(UUID uuid, int id, Transform transform) {
        this.uuid = uuid;
        this.id = id;
        this.transform = transform;
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
}

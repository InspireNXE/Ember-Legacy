/**
 * This file is part of Ember, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014 ObsidianBox <http://obsidianbox.org/>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.obsidianbox.ember.physics;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.obsidianbox.ember.Game;
import org.obsidianbox.ember.component.IComponentHolder;
import org.obsidianbox.ember.physics.util.Transform;

public final class Entity implements IComponentHolder<EntityComponent> {
    public final Game game;
    public final UUID uuid;
    public final int id;
    private Transform transform;
    private boolean isSavable = false;
    private final Set<EntityComponent> components = new HashSet<>();

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
        return this == o || o instanceof Entity && uuid.equals(((Entity) o).uuid);

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

    @Override
    public Collection<EntityComponent> getComponents() {
        return components;
    }
}

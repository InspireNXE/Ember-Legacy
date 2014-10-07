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
package org.obsidianbox.ember.game.entity;

import org.obsidianbox.ember.Ember;
import org.obsidianbox.ember.api.GameObject;
import org.obsidianbox.ember.api.physics.Transform;
import org.obsidianbox.ember.api.physics.TransformProvider;

import java.util.Optional;
import java.util.UUID;

public final class Entity implements GameObject {

    public final Ember game;
    public final UUID uuid;
    public final int id;
    private Optional<TransformProvider> provider;
    private boolean isSavable = false;

    public Entity(Ember game, int id) {
        this(game, UUID.randomUUID(), id);
    }

    protected Entity(Ember game, UUID uuid, int id) {
        this.game = game;
        this.uuid = uuid;
        this.id = id;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public Ember getGame() {
        return game;
    }

    public Optional<Transform> getTransform() {
        return !provider.isPresent() ? Optional.empty() : provider.get().getTransform();
    }

    public void setTransformProvider(TransformProvider provider) {
        this.provider = Optional.ofNullable(provider);
    }

    public boolean isSavable() {
        return isSavable;
    }

    public void setSavable(boolean isSavable) {
        this.isSavable = isSavable;
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
               ", transform=" + (!provider.isPresent() ? "none" : provider.get().getTransform()) +
               ", isSavable=" + isSavable +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        return this == o || !(o == null || getClass() != o.getClass()) && uuid.equals(((Entity) o).uuid);
    }
}

/**
 * This file is part of Ember, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014-2015 InspireNXE <http://inspirenxe.org/>
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
package org.obsidianbox.ember.game.universe.level;

import org.obsidianbox.ember.api.universe.level.Chunk;
import org.obsidianbox.ember.api.universe.level.Location;

import java.lang.ref.WeakReference;
import java.util.Optional;

public final class ChunkReference {

    private final Location location;
    private WeakReference<Chunk> reference;

    public ChunkReference(Chunk referent) {
        reference = new WeakReference<>(referent);
        location = referent.getLocation();
    }

    protected void setReference(ChunkImpl chunk) {
        if (location != chunk.location) {
            throw new RuntimeException("Attempted to update chunk reference with incorrect location!");
        }
        reference = new WeakReference<>(chunk);
    }

    public Optional<Chunk> getChunk() {
        return Optional.ofNullable(reference.get());
    }

    @Override
    public boolean equals(Object o) {
        return this == o || !(o == null || getClass() != o.getClass()) && location.equals(((ChunkReference) o).location);
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }
}

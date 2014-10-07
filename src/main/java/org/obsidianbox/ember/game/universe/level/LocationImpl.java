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
package org.obsidianbox.ember.game.universe.level;

import org.obsidianbox.ember.api.universe.level.Chunk;
import org.obsidianbox.ember.api.universe.level.LoadOption;
import org.obsidianbox.ember.api.universe.level.Location;
import org.obsidianbox.ember.api.universe.level.World;
import org.obsidianbox.ember.api.universe.material.Material;

import java.util.Optional;

public final class LocationImpl implements Location {

    public final WorldImpl world;
    public final int x, y, z;
    private final ChunkReference reference;
    private final boolean isChunkLocation;

    public LocationImpl(WorldImpl world, int x, int y, int z, boolean isChunkLocation) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.isChunkLocation = isChunkLocation;
        reference =
                new ChunkReference(
                        isChunkLocation ? world.getChunkExact(x, y, z, LoadOption.NO_LOAD).get() : world.getChunk(x, y, z, LoadOption.NO_LOAD).get());
    }

    protected LocationImpl(WorldImpl world, int cx, int cy, int cz) {
        this.world = world;
        this.x = cx;
        this.y = cy;
        this.z = cz;
        isChunkLocation = true;
        reference = new ChunkReference(null);
    }

    protected void setChunkReference(ChunkImpl chunk) {
        reference.setReference(chunk);
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public Optional<Chunk> getChunk() {
        return reference.getChunk();
    }

    @Override
    public Optional<Material> getMaterial() {
        if (getChunk().isPresent()) {
            if (isChunkLocation) {
                // TODO: Return material from chunk's base
            } else {
                return getChunk().get().getMaterial(x, y, z);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final LocationImpl location = (LocationImpl) o;

        return x == location.x && y == location.y && z == location.z && world.equals(location.world);
    }

    @Override
    public int hashCode() {
        int result = world.hashCode();
        result = 31 * result + x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }
}

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
package org.obsidianbox.ember.universe.voxel;

import org.obsidianbox.ember.universe.Location;
import org.obsidianbox.ember.universe.cuboid.Chunk;
import org.obsidianbox.ember.universe.cuboid.ChunkReference;
import org.obsidianbox.ember.universe.voxel.material.IMaterial;

import java.util.Optional;

public final class Voxel {
    public final Location location;
    private final ChunkReference reference;

    public Voxel(Chunk chunk, Location location) {
        this.location = location;
        reference = new ChunkReference(chunk);
    }

    public Optional<IMaterial> getMaterial() {
        if (reference.get() != null) {
            return reference.get().getMaterial(location.x, location.y, location.z);
        }
        return Optional.empty();
    }

    public void setMaterial(IMaterial material) {
        if (reference.get() != null) {
            reference.get().setMaterial(location.x, location.y, location.z, material);
        }
    }
}

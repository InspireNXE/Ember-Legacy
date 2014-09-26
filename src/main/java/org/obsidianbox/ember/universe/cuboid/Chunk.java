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
package org.obsidianbox.ember.universe.cuboid;

import com.flowpowered.commons.BitSize;
import org.obsidianbox.ember.storage.atomic.AtomicPaletteStringStore;
import org.obsidianbox.ember.universe.Location;
import org.obsidianbox.ember.universe.voxel.Voxel;
import org.obsidianbox.ember.universe.voxel.material.IMaterial;

import java.util.Optional;

public class Chunk {
    public static final BitSize BITS = new BitSize(4);
    public final Location location;
    private final AtomicPaletteStringStore store;

    public Chunk(Location location) {
        this.location = location;
        store = new AtomicPaletteStringStore(BITS, location.world.game.getMaterialManager().getMaterialIdMap());
    }

    public Optional<Voxel> getVoxel(int vx, int vy, int vz) {
        if (!isInsideChunk(vx, vy, vz)) {
            return Optional.empty();
        }
        return Optional.of(new Voxel(this, new Location(location.world, vx, vy, vz)));
    }

    public Optional<IMaterial> getMaterial(int vx, int vy, int vz) {
        if (!isInsideChunk(vx, vy, vz)) {
            return Optional.empty();
        }
        final Optional<String> materialName = store.get(vx, vy, vz);
        if (materialName.isPresent()) {
            return location.world.game.getMaterialManager().get(materialName.get());
        }
        return Optional.empty();
    }

    public boolean setMaterial(int vx, int vy, int vz, IMaterial material) {
        return isInsideChunk(vx, vy, vz) && store.set(vx, vy, vz, material.getName());
    }

    private boolean isInsideChunk(int vx, int vy, int vz) {
        final int cx = vx << BITS.BITS;
        final int cy = vy << BITS.BITS;
        final int cz = vz << BITS.BITS;

        return location.x == cx && location.y == cy && location.z == cz;
    }
}

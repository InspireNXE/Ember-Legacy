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
package org.obsidianbox.ember.world.cuboid;

import com.flowpowered.commons.BitSize;
import com.flowpowered.math.vector.Vector3i;
import org.obsidianbox.ember.storage.atomic.AtomicPaletteStringStore;
import org.obsidianbox.ember.world.World;
import org.obsidianbox.ember.world.voxel.Voxel;
import org.obsidianbox.ember.world.voxel.material.IMaterial;

import java.util.Optional;

public class Chunk {
    public static final BitSize BITS = new BitSize(4);
    public final World world;
    public final Vector3i position;
    private final AtomicPaletteStringStore store;

    public Chunk(World world, Vector3i position) {
        this.world = world;
        this.position = position;
        store = new AtomicPaletteStringStore(BITS, world.game.getMaterialManager().getMaterialIdMap());
    }

    public Optional<Voxel> getVoxel(int x, int y, int z) {
        if (!isInsideChunk(x, y, z)) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    private boolean isInsideChunk(int vx, int vy, int vz) {
        final int cx = vx << BITS.BITS;
        final int cy = vy << BITS.BITS;
        final int cz = vz << BITS.BITS;

        return position.getX() == cx && position.getY() == cy && position.getZ() == cz;
    }
}

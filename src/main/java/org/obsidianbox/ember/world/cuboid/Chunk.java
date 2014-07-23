/**
 * This file is part of Ember, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014-2014 ObsidianBox <http://obsidianbox.org/>
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

import com.flowpowered.math.vector.Vector3i;
import org.obsidianbox.ember.world.World;
import org.obsidianbox.ember.world.voxel.Material;
import org.obsidianbox.ember.world.voxel.Voxel;

public class Chunk {
    public final World world;
    public final Vector3i position;
    private final Voxel[][][] voxels;

    public Chunk(World world, Vector3i position) {
        this.world = world;
        this.position = position;
        voxels = new Voxel[16][16][16];
        clear();
    }

    public Voxel getVoxel(Vector3i position) {
        return voxels[position.getX()][position.getY()][position.getZ()];
    }

    public void setMaterial(Vector3i position, Material material) {
         voxels[position.getX()][position.getY()][position.getZ()] = new Voxel(position, material);
    }

    public void fill(Material material) {
        for (int i = 0; i < voxels.length; i++) {
            for (int j = 0; j < i; j++) {
                for (int k = 0; k < j; k++) {
                    voxels[i][j][k] = new Voxel(new Vector3i(i, j, k), material);
                }
            }
        }
    }

    public void clear() {
        for (int i = 0; i < voxels.length; i++) {
            for (int j = 0; j < i; j++) {
                for (int k = 0; k < j; k++) {
                    voxels[i][j][k] = new Voxel(new Vector3i(i, j, k));
                }
            }
        }
    }
}

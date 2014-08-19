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
package org.obsidianbox.ember.world;

import com.flowpowered.commons.ticking.TickingElement;
import com.flowpowered.math.vector.Vector3i;
import org.obsidianbox.ember.world.cuboid.Chunk;

import java.util.concurrent.ConcurrentHashMap;

public class World extends TickingElement {
    public final String identifier;
    private final ConcurrentHashMap<Vector3i, Chunk> chunks = new ConcurrentHashMap<>();

    public World(String identifier) {
        super("Ember - World - " + identifier, 20);
        this.identifier = identifier;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onTick(long l) {

    }

    @Override
    public void onStop() {

    }

    public Chunk getChunk(Vector3i position) {
        Chunk c = chunks.get(position);
        if (c == null) {
            c = new Chunk(this, position);
        }
        return c;
    }
}

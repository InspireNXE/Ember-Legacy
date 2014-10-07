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
package org.obsidianbox.ember.api.util.atomic;

import com.flowpowered.commons.BitSize;
import com.flowpowered.commons.StringToUniqueIntegerMap;
import com.flowpowered.math.vector.Vector3f;
import com.flowpowered.math.vector.Vector3i;
import org.obsidianbox.ember.api.util.ITypeStore;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * TODO StringToUniqueIntegerMap isn't atomic, needs to be
 *
 * TODO Need to make accessing parts of this atomic...
 */
public class AtomicPaletteStringStore implements ITypeStore<String> {

    private final BitSize bits;
    private final StringToUniqueIntegerMap stringMap;
    private final AtomicIntegerArray store;
    private final AtomicBoolean dirty = new AtomicBoolean(false);

    public AtomicPaletteStringStore(BitSize bits, StringToUniqueIntegerMap stringMap) {
        this.bits = bits;
        this.stringMap = stringMap;
        store = new AtomicIntegerArray(1 << (bits.BITS * 3));
        setDirty(true);
    }

    @Override
    public Optional<String> get(int x, int y, int z) {
        final int index = getIndex(x, y, z);
        // 0 <= i < area
        if (index < 0 || index > bits.VOLUME) {
            Optional.empty();
        }
        final int rawId = store.get(index);
        return Optional.ofNullable(stringMap.getString(rawId));
    }

    @Override
    public Optional<String> get(Vector3i position) {
        return get(position.getX(), position.getY(), position.getZ());
    }

    @Override
    public Optional<String> get(Vector3f position) {
        return get(position.getFloorX(), position.getFloorY(), position.getFloorZ());
    }

    @Override
    public Optional<String> getAndSet(int x, int y, int z, String value) {
        final Optional<String> previous = get(x, y, z);
        set(x, y, z, value);
        return previous;
    }

    @Override
    public Optional<String> getAndSet(Vector3i position, String value) {
        return getAndSet(position.getX(), position.getY(), position.getZ(), value);
    }

    @Override
    public Optional<String> getAndSet(Vector3f position, String value) {
        return getAndSet(position.getFloorX(), position.getFloorY(), position.getFloorZ(), value);
    }

    @Override
    public boolean set(int x, int y, int z, String value) {
        final int index = getIndex(x, y, z);
        // 0 <= i < area
        if (index < 0 || index > bits.VOLUME) {
            return false;
        }

        final Integer mapped = stringMap.getValue(value);
        if (mapped == null) {
            return false;
        }
        store.set(index, mapped);
        return true;
    }

    @Override
    public boolean set(Vector3i position, String value) {
        return set(position.getX(), position.getY(), position.getZ(), value);
    }

    @Override
    public boolean set(Vector3f position, String value) {
        return set(position.getFloorX(), position.getFloorY(), position.getFloorZ(), value);
    }

    @Override
    public boolean isUniform() {
        final int first = store.get(0);
        for (int i = 0; i < store.length(); i++) {
            if (store.get(i) != first) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isDirty() {
        return dirty.get();
    }

    @Override
    public boolean setDirty(boolean dirty) {
        return this.dirty.getAndSet(dirty);
    }

    private int getIndex(int x, int y, int z) {
        return (x << bits.BITS) + z + (y << (bits.BITS * 2));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {length= " + store.length() + ", values= {" + store + "}}";
    }
}

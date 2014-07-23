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
package org.obsidianbox.ember.world.storage;

import gnu.trove.map.hash.TShortObjectHashMap;

import java.util.Collection;

public class TypedIdRegistry<T> {
    public static final short ID_NOT_FOUND = -1;
    private final Class<T> clazz;
    private final TShortObjectHashMap<T> REGISTRY;

    public TypedIdRegistry(Class<T> clazz) {
        this.clazz = clazz;
        REGISTRY = new TShortObjectHashMap<>();
    }

    public TypedIdRegistry(Class<T> clazz, Collection<T> initial) {
        this.clazz = clazz;
        REGISTRY = new TShortObjectHashMap<>(initial.size());
        putAll(initial);
    }

    public T get(short key) {
        return REGISTRY.get(key);
    }

    public short get(final T value) {
        // Since java doesn't have closures...
        final IdContainer key = new IdContainer();
        key.id = ID_NOT_FOUND;
        REGISTRY.forEachEntry((i, t) -> {
            if (value.equals(t)) {
                key.id = i;
                return true;
            }
            return false;
        });
        return key.id;
    }

    public void put(T t) {
        putIncrement(t);
    }

    private void putIncrement(T t) {
        if (REGISTRY.containsValue(t)) {
            return;
        }
        short id = (short) REGISTRY.size();
        if (id != 0) {
            id += 1;
        }
        REGISTRY.put(id, t);
    }

    public void putAll(Collection<T> collection) {
        for (T t : collection) {
            putIncrement(t);
        }
    }

    @Override
    public String toString() {
        return "TypedIdRegistry{" +
                "Type=" + clazz +
                "Size=" + REGISTRY.size() +
                "Values={" + REGISTRY.toString() +
                "}}";
    }
}

final class IdContainer {
    public short id;
}

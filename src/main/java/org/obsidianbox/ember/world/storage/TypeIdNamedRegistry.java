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
package org.obsidianbox.ember.world.storage;

import com.flowpowered.commons.Named;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class TypeIdNamedRegistry<T extends Named> {
    private final Class<T> clazz;
    private final Map<Short, T> ID_MAP;
    private final Map<String, T> STRING_MAP;

    public TypeIdNamedRegistry(Class<T> clazz) {
        this.clazz = clazz;
        ID_MAP = new HashMap<>();
        STRING_MAP = new HashMap<>();
    }

    public Optional<T> get(short key) {
        return ID_MAP.entrySet().stream().filter(en -> en.getKey() == key).map(Map.Entry::getValue).findAny();
    }

    public Optional<Short> get(final T value) {
        return ID_MAP.entrySet().stream().filter(en -> en.getValue().equals(value)).map(Map.Entry::getKey).findAny();
    }

    public Optional<T> get(String key) {
        return STRING_MAP.entrySet().stream().filter(en -> en.getKey().equals(key)).map(Map.Entry::getValue).findAny();
    }

    public T add(T t) {
        return addAndIncrementId(t);
    }

    private T addAndIncrementId(T t) {
        if (STRING_MAP.keySet().stream().anyMatch(en -> en.equals(t.getName()))) {
            return t;
        }
        STRING_MAP.put(t.getName(), t);
        short id = (short) ID_MAP.size();
        if (id != 0) {
            id += 1;
        }
        ID_MAP.put(id, t);
        return t;
    }

    @Override
    public String toString() {
        return "TypeIdNamedRegistry{" +
                "type=" + clazz +
                ", ID_MAP=" + ID_MAP +
                ", STRING_MAP=" + STRING_MAP +
                '}';
    }
}

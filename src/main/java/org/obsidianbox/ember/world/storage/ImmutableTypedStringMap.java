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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Represents a {@link java.util.Map} of {@link T} references keyed to {@link java.lang.String}s. This map is concrete;
 * no references are allowed to be removed.
 */
public final class ImmutableTypedStringMap<T> {
    private final Map<String, T> wrapped = new HashMap<>();

    public Optional<T> get(String value) {
        return wrapped.entrySet().stream().filter(en -> en.getKey().equals(value)).map(Map.Entry::getValue).findAny();
    }

    public Optional<String> get(T value) {
        return wrapped.entrySet().stream().filter(en -> en.getValue().equals(value)).map(Map.Entry::getKey).findAny();
    }

    public T add(String key, T value) {
        if (wrapped.keySet().stream().anyMatch(en -> en.equals(key))) {
            return value;
        }
        wrapped.put(key, value);
        return value;
    }

    public Stream<Map.Entry<String, T>> stream() {
        return wrapped.entrySet().stream();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImmutableTypedStringMap that = (ImmutableTypedStringMap) o;

        return wrapped.equals(that.wrapped);

    }

    @Override
    public int hashCode() {
        return wrapped.hashCode();
    }


    @Override
    public String toString() {
        return "ImmutableTypedStringMap " + wrapped;
    }
}

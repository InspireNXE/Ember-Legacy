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
package org.obsidianbox.ember.api.util;

import com.flowpowered.math.vector.Vector3f;
import com.flowpowered.math.vector.Vector3i;

import java.util.Optional;

/**
 * Stores a {@link T} at a x, y, z
 */
public interface ITypeStore<T> {

    public Optional<T> get(int x, int y, int z);

    public Optional<T> get(Vector3i position);

    public Optional<T> get(Vector3f position);

    public Optional<T> getAndSet(int x, int y, int z, T value);

    public Optional<T> getAndSet(Vector3i position, T value);

    public Optional<T> getAndSet(Vector3f position, T value);

    public boolean set(int x, int y, int z, T value);

    public boolean set(Vector3i position, T value);

    public boolean set(Vector3f position, T value);

    public boolean isUniform();

    public boolean isDirty();

    public boolean setDirty(boolean dirty);
}

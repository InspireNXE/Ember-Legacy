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
package org.obsidianbox.ember.component;

import org.obsidianbox.ember.IGameObject;

import java.util.Collection;
import java.util.Optional;

public interface IComponentHolder<T extends IComponent> extends IGameObject {
    default Optional<T> add(Class<? extends IComponent<? extends IComponentHolder>> clazz) {
        final Optional<T> existing = getComponents().stream().filter(en -> en.getClass().equals(clazz)).findAny();
        if (existing.isPresent()) {
            return existing;
        }

        try {
            T instanced = (T) clazz.newInstance();
            getComponents().add(instanced);
            instanced.attach(this);
            return Optional.of(instanced);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    default Optional<T> get(Class<? extends IComponent<? extends IComponentHolder>> clazz) {
        return getComponents().stream().filter(en -> en.getClass().equals(clazz)).findFirst();
    }

    default Optional<T> remove(Class<? extends IComponent<? extends IComponentHolder>> clazz) {
        Optional<T> found = getComponents().stream().filter(en -> en.getClass().equals(clazz)).findAny();
        if (found.isPresent()) {
            getComponents().remove(found.get());
            found.get().detach();
        }
        return found;
    }

    public Collection<T> getComponents();
}

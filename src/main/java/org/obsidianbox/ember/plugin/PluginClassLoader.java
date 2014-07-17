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
package org.obsidianbox.ember.plugin;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PluginClassLoader extends URLClassLoader {
    private static final Set<PluginClassLoader> LOADERS = new HashSet<>();
    private static final Map<String, Class<?>> NAMES_BY_CLASSES = new HashMap<>();

    public PluginClassLoader(URL pluginResource) {
        super(new URL[] {pluginResource}, PluginClassLoader.class.getClassLoader());
        LOADERS.add(this);
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return findClass0(name, true);
    }

    private Class<?> findClass0(String name, boolean checkOtherPlugins) throws ClassNotFoundException {
        Class<?> found = NAMES_BY_CLASSES.get(name);
        if (found == null) {
            try {
                found = super.findClass(name);
            } catch (ClassNotFoundException ignore) {
                if (checkOtherPlugins) {
                    for (PluginClassLoader loader : LOADERS) {
                        if (loader == this) {
                            continue;
                        }
                        try {
                            found = loader.findClass0(name, false);
                            break;
                        } catch (ClassNotFoundException ignored) {
                        }
                    }
                }
            }
            if (found == null) {
                throw new ClassNotFoundException(name);
            } else {
                NAMES_BY_CLASSES.put(name, found);
            }
        }
        return found;
    }
}

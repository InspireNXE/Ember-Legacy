package com.obsidianbox.ember.plugin;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PluginClassLoader extends URLClassLoader {
    private static final Set<PluginClassLoader> LOADERS = new HashSet<>();
    private static final Map<String, Class> NAMES_BY_CLASSES = new HashMap<>();

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

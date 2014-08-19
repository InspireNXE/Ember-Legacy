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
package org.obsidianbox.ember.plugins;

import com.flowpowered.plugins.*;
import com.flowpowered.plugins.annotated.AnnotatedPluginLoader;
import com.flowpowered.plugins.simple.SimplePluginLoader;
import org.obsidianbox.ember.Game;
import org.obsidianbox.ember.event.PluginEvent;
import org.obsidianbox.ember.resource.FileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLClassLoader;

public class GamePluginManager extends PluginManager<GameContext> {
    public final Game game;
    protected final ContextCreator<GameContext> creator;

    public GamePluginManager(Game game) {
        super(game.logger, new GamePluginLoggerFactory());
        this.game = game;

        this.creator = plugin -> new GameContext(game, plugin);

        addLoader(new SimplePluginLoader<>(creator, new URLClassLoader(FileSystem.asArray(FileSystem.getURLs(FileSystem.PLUGINS_PATH, "*.jar")), getClass().getClassLoader())));
        addLoader(new AnnotatedPluginLoader<>(creator, FileSystem.PLUGINS_PATH, getClass().getClassLoader()));
    }

    @Override
    public void enable(Plugin<GameContext> plugin) throws Exception {
        super.enable(plugin);
        game.getEventManager().callEvent(new PluginEvent.Enabled(game, plugin));
        // TODO Register plugin main classes as Listener objects when flow-commands supports Objects as listeners.
    }

    @Override
    public void disable(Plugin<GameContext> plugin) throws Exception {
        super.disable(plugin);
        game.getEventManager().callEvent(new PluginEvent.Disabled(game, plugin));
    }

    protected ContextCreator<GameContext> getCreator() {
        return creator;
    }
}

final class GamePluginLoggerFactory implements PluginLoggerFactory {
    @Override
    public Logger getLogger(String pluginName) {
        return LoggerFactory.getLogger(pluginName);
    }
}

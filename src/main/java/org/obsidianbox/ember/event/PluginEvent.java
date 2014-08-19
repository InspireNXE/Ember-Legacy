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
package org.obsidianbox.ember.event;

import com.flowpowered.plugins.Plugin;
import org.obsidianbox.ember.Game;
import org.obsidianbox.ember.plugins.GameContext;

public abstract class PluginEvent extends GameEvent {
    public final Plugin<GameContext> plugin;

    public PluginEvent(Game game, Plugin<GameContext> plugin) {
        super(game);
        this.plugin = plugin;
    }

    /**
     * Fired when a {@link com.flowpowered.plugins.Plugin} is disabled.
     */
    public static class Disabled extends PluginEvent {
        public Disabled(Game game, Plugin<GameContext> plugin) {
            super(game, plugin);
        }
    }

    /**
     * Fired when a {@link com.flowpowered.plugins.Plugin} is enabled.
     */
    public static class Enabled extends PluginEvent {
        public Enabled(Game game, Plugin<GameContext> plugin) {
            super(game, plugin);
        }
    }
}

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
package com.obsidianbox.ember.plugin.example;

import com.flowpowered.events.EventHandler;
import com.flowpowered.events.Order;
import com.obsidianbox.ember.event.GameEvent;
import com.obsidianbox.ember.event.PluginEvent;
import com.obsidianbox.ember.plugin.Plugin;

@Plugin(id = "ExamplePlugin", name = "Example", version = "1.0.0-SNAPSHOT")
public class Example {
    @EventHandler
    public void onPluginLoad(PluginEvent.PluginLoadEvent event) {
        event.game.logger.info("I've been loaded :)!");
    }

    @EventHandler
    public void onPluginEnable(PluginEvent.PluginEnableEvent event) {
        event.game.logger.info("I've been enabled :D!");
    }

    @EventHandler
    public void onPluginDisable(PluginEvent.PluginDisableEvent event) {
        event.game.logger.info("I've been disabled D:!");
    }

    @EventHandler
    public void onPluginReload(PluginEvent.PluginReloadEvent event) {
        event.game.logger.info("I've been reloaded >:D!");
    }

    @EventHandler()
    public void onGameStart(GameEvent.GameStartEvent event) {
        event.game.logger.info("GameStartEvent fired!");
    }
}

/**
 * This file is part of Ember, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014-2015 InspireNXE <http://inspirenxe.org/>
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
package org.obsidianbox.ember.game.universe;

import com.flowpowered.commons.ticking.TickingElement;
import com.flowpowered.plugins.Plugin;
import org.obsidianbox.ember.game.Ember;
import org.obsidianbox.ember.api.universe.Universe;
import org.obsidianbox.ember.game.event.WorldEvent;
import org.obsidianbox.ember.api.universe.generator.WorldGenerator;
import org.obsidianbox.ember.game.universe.level.WorldImpl;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UniverseImpl extends TickingElement implements Universe {

    public final Ember game;
    private final ConcurrentHashMap<UUID, WorldImpl> worlds = new ConcurrentHashMap<>();

    public UniverseImpl(Ember game) {
        super("universe", 20);
        this.game = game;
    }

    @Override
    public void onStart() {
        Ember.LOGGER.info("Starting universe");
    }

    @Override
    public void onTick(long dt) {

    }

    @Override
    public void onStop() {
        worlds.forEachValue(worlds.size(), TickingElement::stop);
    }

    @Override
    public WorldImpl loadWorld(Plugin plugin, String identifier, WorldGenerator generator) {
        for (WorldImpl w : worlds.values()) {
            if (w.identifier.equalsIgnoreCase(identifier)) {
                return w;
            }
        }
        // TODO: Load world from file if present
        final WorldImpl world = new WorldImpl(game, plugin, identifier, generator);
        // TODO: Fire appropriate event based on if the world was just created or not
        game.getEventManager().callEvent(new WorldEvent.Create(game, world));
        world.start();
        game.getEventManager().callEvent(new WorldEvent.Load(game, world));
        worlds.put(world.uuid, world);
        return world;
    }
}

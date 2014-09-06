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
package org.obsidianbox.ember;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

import org.obsidianbox.ember.plugin.GamePluginManager;
import org.obsidianbox.ember.world.voxel.material.MaterialManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flowpowered.commons.ticking.TickingElement;
import com.flowpowered.events.SimpleEventManager;

import org.obsidianbox.ember.command.Commands;
import org.obsidianbox.ember.command.GameCommandManager;
import org.obsidianbox.ember.console.GameConsole;
import org.obsidianbox.ember.event.GameEvent;
import org.obsidianbox.ember.network.Network;
import org.obsidianbox.ember.resource.FileSystem;

public final class Game extends TickingElement {
    public final Logger logger = LoggerFactory.getLogger("Ember");
    private static final String version;
    private final Semaphore semaphore = new Semaphore(0);
    private final AtomicBoolean running = new AtomicBoolean(false);
    private GameConsole console;
    private GameCommandManager commandManager;
    private GamePluginManager pluginManager;
    private SimpleEventManager eventManager;
    private FileSystem fileSystem;
    private MaterialManager materialManager;

    //Modules
    public final Network network;

    static {
        version = Game.class.getPackage().getImplementationVersion();
    }

    public Game() {
        super("ember - game", 20);
        this.network = new Network(this);
    }

    @Override
    public void onStart() {
        logger.info("Starting Ember, please wait a moment");
        logger.info("Ember is ALPHA software and as such things may not work correctly or at all. Help out the team and " +
                "report all issues to https://github.com/InspireNXE/Ember/issues");
        running.set(true);
        try {
            console = new GameConsole(this);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        eventManager = new SimpleEventManager(logger);
        commandManager = new GameCommandManager(this);
        commandManager.create(new Commands(this));
        eventManager.registerEvents(commandManager, commandManager);
        fileSystem = new FileSystem(this);
        try {
            fileSystem.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        network.start();
        materialManager = new MaterialManager();
        pluginManager = new GamePluginManager(this);
        pluginManager.enable();
        eventManager.callEvent(new GameEvent.Start(this));
    }

    @Override
    public void onTick(long dt) {
        eventManager.callEvent(new GameEvent.Tick(this, GameEvent.Tick.Phase.START, dt));
        eventManager.callEvent(new GameEvent.Tick(this, GameEvent.Tick.Phase.END, dt));
    }

    @Override
    public void onStop() {
        eventManager.callEvent(new GameEvent.Stop(this));
        pluginManager.disable();
        network.stop();
        console.close();
    }

    public void open() {
        if (running.compareAndSet(false, true)) {
            start();
            semaphore.acquireUninterruptibly();
            stop();
        }
    }

    public void close() {
        if (running.compareAndSet(true, false)) {
            stop();
            semaphore.release();
        }
    }

    public GameConsole getConsole() {
        return console;
    }

    public GameCommandManager getCommandManager() {
        return commandManager;
    }

    public SimpleEventManager getEventManager() {
        return eventManager;
    }

    public FileSystem getFileSystem() { return fileSystem; }

    public MaterialManager getMaterialManager() {
        return materialManager;
    }

    public String getVersion() {
        return version;
    }
}

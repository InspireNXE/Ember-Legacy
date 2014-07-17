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
    private final Semaphore semaphore = new Semaphore(0);
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final AtomicBoolean isMain = new AtomicBoolean(false);
    private GameConsole console;
    private GameCommandManager commandManager;
    private SimpleEventManager eventManager;
    private FileSystem fileSystem;
    //Modules
    public final Network network;

    public Game() {
        super("Game", 20);
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
        commandManager = new GameCommandManager(this);
        commandManager.create(new Commands(this));
        eventManager = new SimpleEventManager(logger);
        fileSystem = new FileSystem(this);
        try {
            fileSystem.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        network.start();
        eventManager.callEvent(new GameEvent.Start(this));
    }

    @Override
    public void onTick(long dt) {
        commandManager.onTick(dt);
        eventManager.callEvent(new GameEvent.Tick(this, GameEvent.Tick.Phase.START, dt));
        // TODO All game management here
        eventManager.callEvent(new GameEvent.Tick(this, GameEvent.Tick.Phase.END, dt));
    }

    @Override
    public void onStop() {
        eventManager.callEvent(new GameEvent.Stop(this));
        network.stop();
        console.close();
    }

    public void open(boolean lockMain) {
        if (running.compareAndSet(false, true)) {
            start();
            if (lockMain) {
                isMain.set(true);
                semaphore.acquireUninterruptibly();
                stop();
            }
        }
    }

    public void close() {
        if (running.compareAndSet(true, false)) {
            stop();
            if (isMain.compareAndSet(true, false)) {
                semaphore.release();
            }
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
}

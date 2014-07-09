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
package com.obsidianbox.ember;

import com.flowpowered.commands.CommandException;
import com.flowpowered.commands.CommandManager;
import com.flowpowered.commands.CommandProvider;
import com.flowpowered.commands.annotated.AnnotatedCommandExecutorFactory;
import com.flowpowered.commons.ticking.TickingElement;
import com.flowpowered.events.SimpleEventManager;
import com.obsidianbox.ember.command.Commands;
import com.obsidianbox.ember.command.ConsoleCommandSender;
import com.obsidianbox.ember.console.GameConsole;
import com.obsidianbox.ember.event.GameEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public final class Game extends TickingElement {
    public final Logger logger = LoggerFactory.getLogger("Ember");
    private final Semaphore semaphore = new Semaphore(0);
    private final AtomicBoolean running = new AtomicBoolean(false);
    private Queue<String> rawCommandQueue;
    private GameConsole console;
    private SimpleEventManager eventManager;
    private ConsoleCommandSender sender;

    public Game() {
        super("Game", 20);
    }

    public SimpleEventManager getEventManager() {
        return eventManager;
    }

    /**
     * Starts the game and causes the current thread to wait until the {@link #close()} method is called. When this happens, the thread resumes and the game is stopped. Interrupting the thread will
     * not cause it to close, only calling {@link #close()} will. Calls to {@link #close()} before open() are not counted.
     */
    public void open() {
        // Only start the game if running has a value of false, in which case it's set to true and the if statement passes
        if (running.compareAndSet(false, true)) {
            // Start the threads, which might release permits by calling close() before all are started
            start();
            // Attempts to acquire a permit, but since none are available (except for the situation stated above), the thread blocks
            semaphore.acquireUninterruptibly();
            // A permit was acquired, which means close() was called; so we stop game. The available permit count returns to zero
            stop();
        }
    }


    /**
     * Wakes up the thread that has opened the game (by having called {@link #open()}) and allows it to resume it's activity to trigger the end of the game.
     */
    public void close() {
        // Only stop the game if running has a value of true, in which case it's set to false and the if statement passes
        if (running.compareAndSet(true, false)) {
            // Release a permit (which doesn't need to be held by the thread in the first place),
            // allowing the main thread to acquire one and resume to close the game
            stop();
            semaphore.release();
            // The available permit count is now non-zero
        }
    }

    @Override
    public void onStart() {
        running.set(true);
        console = new GameConsole(this);
        rawCommandQueue = new LinkedBlockingQueue<>();
        final CommandManager manager = new CommandManager(false);
        final CommandProvider provider = new CommandProvider() {
            @Override
            public String getName() {
                return "game";
            }
        };
        manager.setRootCommand(manager.getCommand(provider, "root"));
        sender = new ConsoleCommandSender(manager);
        new AnnotatedCommandExecutorFactory(manager, provider, LoggerFactory.getLogger("Ember")).create(new Commands(this));

        eventManager = new SimpleEventManager(logger);
        eventManager.registerEvents(new GameListener(this), this);
        eventManager.callEvent(new GameEvent.GameStartEvent(this));
    }

    @Override
    public void onTick(long dt) {
        // Process commands
        for (String rawCommand : rawCommandQueue) {
            try {
                sender.processCommand(rawCommand);
            } catch (CommandException e) {
                logger.error("Except caught processing command: " + rawCommand, e);
            }
        }
    }

    @Override
    public void onStop() {
        console.close();
        eventManager.callEvent(new GameEvent.GameStopEvent(this));
    }

    public Queue<String> getRawCommandQueue() {
        return rawCommandQueue;
    }
}

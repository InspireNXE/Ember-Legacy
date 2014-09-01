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
package org.obsidianbox.ember.command;

import com.flowpowered.commands.Command;
import com.flowpowered.commands.CommandException;
import com.flowpowered.commands.CommandManager;
import com.flowpowered.commands.CommandProvider;
import com.flowpowered.commands.annotated.AnnotatedCommandExecutorFactory;

import com.flowpowered.events.EventHandler;
import com.flowpowered.events.Order;
import org.obsidianbox.ember.Game;
import org.obsidianbox.ember.event.GameEvent;

public class GameCommandManager extends CommandManager {
    private final Game game;
    private AnnotatedCommandExecutorFactory factory;

    public GameCommandManager(Game game) {
        this.game = game;
        final CommandProvider provider = () -> "game";
        setRootCommand(getCommand(provider, "root"));
        factory = new AnnotatedCommandExecutorFactory(this, provider, game.logger);
    }

    @EventHandler (order = Order.EARLIEST)
    public void onTick(GameEvent.Tick event) {
        if (event.phase == GameEvent.Tick.Phase.START) {
            String rawCommand;
            while ((rawCommand = game.getConsole().getCallbackQueue().poll()) != null) {
                try {
                    game.getConsole().processCommand(rawCommand);
                } catch (CommandException e) {
                    game.logger.error("Exception caught processing command: " + rawCommand, e);
                }
            }
        }
    }

    public void create(Class<?> commands) {
        factory.create(commands);
    }

    public void create(Class<?> commands, Command parent) {
        factory.create(commands, parent);
    }

    public void create(Object instance) {
        factory.create(instance);
    }

    public void create(Object instance, Command parent) {
        factory.create(instance, parent);
    }
}

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
package org.obsidianbox.ember.game.console;

import com.flowpowered.chat.ChatReceiver;
import com.flowpowered.commands.CommandArguments;
import com.flowpowered.commands.CommandException;
import com.flowpowered.commands.CommandSender;
import com.flowpowered.commons.console.CommandCallback;
import com.flowpowered.commons.console.JLineConsole;
import com.flowpowered.commons.console.Log4j2JLineConsole;
import com.flowpowered.permissions.PermissionDomain;
import jline.console.completer.Completer;
import org.obsidianbox.ember.game.Ember;
import org.obsidianbox.ember.game.event.GameEvent;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class GameConsole extends Log4j2JLineConsole implements CommandSender {

    private static Field COMMAND_CALLBACK_FIELD;

    static {
        try {
            COMMAND_CALLBACK_FIELD = JLineConsole.class.getDeclaredField("callback");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private final Ember game;
    private final GameCommandCallback callback;

    public GameConsole(Ember game) throws IllegalAccessException {
        super(new GameCommandCallback(), new GameCommandCompleter(), Ember.LOGGER, game.getConfiguration().isDebug() ? 10 : -2, System.out, System.in);
        this.game = game;

        COMMAND_CALLBACK_FIELD.setAccessible(true);
        callback = (GameCommandCallback) COMMAND_CALLBACK_FIELD.get(this);
        COMMAND_CALLBACK_FIELD.setAccessible(false);
    }

    public Queue<String> getCallbackQueue() {
        return callback.callbackQueue;
    }

    @Override
    public void processCommand(String commandLine) throws CommandException {
        game.getCommandManager().getRootCommand().execute(this, new CommandArguments(commandLine.split(" ")));
    }

    @Override
    public void sendMessage(String message) {
        final GameEvent.Chat event = new GameEvent.Chat(game, this, message);
        if (!game.getEventManager().callEvent(event).isCancelled()) {
            Ember.LOGGER.info(event.message);
        }
    }

    @Override
    public void sendMessage(ChatReceiver from, String message) {
        final GameEvent.Chat event = new GameEvent.Chat(game, this, from, message);
        if (!game.getEventManager().callEvent(event).isCancelled()) {
            Ember.LOGGER.info(event.message);
        }
    }

    @Override
    public void sendMessageRaw(String message, String type) {
        final GameEvent.Chat event = new GameEvent.Chat(game, this, message);
        if (!game.getEventManager().callEvent(event).isCancelled()) {
            Ember.LOGGER.info(event.message);
        }
    }

    @Override
    public String getName() {
        return "console";
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }

    @Override
    public boolean hasPermission(String permission, PermissionDomain domain) {
        return true;
    }

    @Override
    public boolean isInGroup(String group) {
        return false;
    }

    @Override
    public boolean isInGroup(String group, PermissionDomain domain) {
        return false;
    }

    @Override
    public Set<String> getGroups() {
        return Collections.emptySet();
    }

    @Override
    public Set<String> getGroups(PermissionDomain domain) {
        return Collections.emptySet();
    }
}

final class GameCommandCallback implements CommandCallback {

    protected final Queue<String> callbackQueue = new LinkedBlockingQueue<>();

    @Override
    public void handleCommand(String command) {
        callbackQueue.offer(command);
    }
}

final class GameCommandCompleter implements Completer {

    @Override
    public int complete(String buffer, int cursor, List<CharSequence> candidates) {
        return 0;
    }
}
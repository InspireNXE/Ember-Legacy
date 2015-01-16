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
package org.obsidianbox.ember.game.player;

import com.flowpowered.chat.ChatReceiver;
import com.flowpowered.commands.CommandArguments;
import com.flowpowered.commands.CommandException;
import com.flowpowered.permissions.PermissionDomain;
import org.obsidianbox.ember.api.network.Session;
import org.obsidianbox.ember.api.player.Player;
import org.obsidianbox.ember.game.Ember;
import org.obsidianbox.ember.game.event.GameEvent;
import org.obsidianbox.ember.game.network.GameSession;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public final class PlayerImpl implements Player {

    public final Ember game;
    public final String name;
    public final Optional<Session> session;
    public final UUID uuid;
    public final int id;

    public PlayerImpl(Ember game, String name, GameSession session, int id) {
        this(game, name, session, UUID.randomUUID(), id);
    }

    public PlayerImpl(Ember game, String name, GameSession session, UUID uuid, int id) {
        this.game = game;
        this.name = name;
        this.session = Optional.ofNullable(session);
        this.uuid = uuid;
        this.id = id;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public Ember getGame() {
        return game;
    }

    @Override
    public Optional<Session> getSession() {
        return session;
    }

    public void disconnect() {
        if (!((GameSession) session.get()).isActive()) {
            Ember.LOGGER.error("Player [" + name + "] is still in the game with an inactive session! This could be bad!");
        } else {
            session.get().disconnect();
        }
    }

    @Override
    public void processCommand(String commandLine) throws CommandException {
        game.getCommandManager().executeCommand(this, new CommandArguments(commandLine.split(" ")));
    }

    @Override
    public void sendMessage(String message) {
        game.getEventManager().callEvent(new GameEvent.Chat(game, this, message));
    }

    @Override
    public void sendMessage(ChatReceiver from, String message) {
        game.getEventManager().callEvent(new GameEvent.Chat(game, this, from, message));
    }

    @Override
    public void sendMessageRaw(String message, String type) {
        game.getEventManager().callEvent(new GameEvent.Chat(game, this, message));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasPermission(String permission) {
        return !game.getEventManager().callEvent(new GameEvent.Permission(game, this, permission)).isCancelled();
    }

    @Override
    public boolean hasPermission(String permission, PermissionDomain domain) {
        return !game.getEventManager().callEvent(new GameEvent.Permission(game, this, domain, permission)).isCancelled();
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

    @Override
    public boolean equals(Object o) {
        return this == o || !(o == null || getClass() != o.getClass()) && uuid.equals(((PlayerImpl) o).uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return "Player{" +
               "game=" + game +
               ", name='" + name + '\'' +
               ", session=" + session +
               ", uuid=" + uuid +
               ", id=" + id +
               '}';
    }
}

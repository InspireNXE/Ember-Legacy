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

import com.flowpowered.chat.ChatReceiver;
import com.flowpowered.events.SimpleEvent;
import com.flowpowered.permissions.PermissionDomain;
import com.flowpowered.permissions.PermissionSubject;

import org.obsidianbox.ember.Game;

public abstract class GameEvent extends SimpleEvent {
    public final Game game;

    public GameEvent(Game game) {
        this.game = game;
    }

    /**
     * Fired when the {@link org.obsidianbox.ember.Game} starts.
     */
    public static class Start extends GameEvent {
        public Start(Game game) {
            super(game);
        }
    }

    /**
     * Fired when the {@link org.obsidianbox.ember.Game} stops.
     */
    public static class Stop extends GameEvent {
        public Stop(Game game) {
            super(game);
        }
    }

    /**
     * Fired when a {@link com.flowpowered.chat.ChatReceiver} is sent a message. <p> Calling {@link org.obsidianbox.ember.event.GameEvent.Chat#setCancelled(boolean)} and passing in true will result in
     * the message not being sent to the {@link com.flowpowered.chat.ChatReceiver}.
     */
    public static class Chat extends GameEvent {
        public final ChatReceiver receiver;
        public final ChatReceiver sender;
        public String message;

        public Chat(Game game, ChatReceiver receiver, String message) {
            this(game, receiver, null, message);
        }

        public Chat(Game game, ChatReceiver receiver, ChatReceiver sender, String message) {
            super(game);
            this.receiver = receiver;
            this.sender = sender;
            this.message = message;
        }

        @Override
        public void setCancelled(boolean cancelled) {
            super.setCancelled(cancelled);
        }
    }

    /**
     * Fired when a {@link com.flowpowered.permissions.PermissionSubject} invokes a permission check. <p> Calling {@link org.obsidianbox.ember.event.GameEvent.Permission#setCancelled(boolean)} and
     * passing in true will result in the permission being denied.
     */
    public static class Permission extends GameEvent {
        public final PermissionSubject subject;
        public final String permission;
        public final PermissionDomain domain;

        public Permission(Game game, PermissionSubject subject, String permission) {
            this(game, subject, null, permission);
        }

        public Permission(Game game, PermissionSubject subject, PermissionDomain domain, String permission) {
            super(game);
            this.subject = subject;
            this.domain = domain;
            this.permission = permission;
        }

        @Override
        public void setCancelled(boolean cancelled) {
            super.setCancelled(cancelled);
        }
    }

    /**
     * Fired at various spots in the {@link org.obsidianbox.ember.Game} loop.
     */
    public static class Tick extends GameEvent {
        public final Phase phase;
        public final long dt;

        public Tick(Game game, Phase phase, long dt) {
            super(game);
            this.phase = phase;
            this.dt = dt;
        }

        public enum Phase {
            START,
            END
        }
    }
}

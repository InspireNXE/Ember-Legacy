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

import io.netty.channel.Channel;

import org.obsidianbox.ember.Game;
import org.obsidianbox.ember.network.GameProtocol;
import org.obsidianbox.ember.network.GameSession;
import org.obsidianbox.ember.network.Network;

public abstract class NetworkEvent extends GameEvent {
    public NetworkEvent(Network network) {
        super(network.game);
    }

    /**
     * Fired when a new {@link org.obsidianbox.ember.network.GameSession} is about to be created. <p> Use this to set the {@link org.obsidianbox.ember.network.GameProtocol} for the session being
     * created.
     */
    public static class PreSessionCreate extends NetworkEvent {
        public final Channel channel;
        public GameProtocol protocol;

        public PreSessionCreate(Network network, Channel channel) {
            super(network);
            this.channel = channel;
        }
    }

    /**
     * Fired when a new {@link org.obsidianbox.ember.network.GameSession} has been created.
     */
    public static class PostSessionCreate extends NetworkEvent {
        public final GameSession session;

        public PostSessionCreate(Network network, GameSession session) {
            super(network);
            this.session = session;
        }
    }

    /**
     * Fired when a {@link org.obsidianbox.ember.network.GameSession} is ready for {@link com.flowpowered.networking.Message}s.
     */
    public static class SessionReady extends NetworkEvent {
        public final GameSession session;

        public SessionReady(Network network, GameSession session) {
            super(network);
            this.session = session;
        }
    }

    /**
     * Fired when a {@link org.obsidianbox.ember.network.GameSession} is about to be disconnected.
     */
    public static class PreSessionDisconnect extends NetworkEvent {
        public final GameSession session;

        public PreSessionDisconnect(Network network, GameSession session) {
            super(network);
            this.session = session;
        }
    }

    /**
     * Fired when a {@link org.obsidianbox.ember.network.GameSession} is disconnected.
     */
    public static class PostSessionDisconnect extends NetworkEvent {
        public final GameSession session;

        public PostSessionDisconnect(Network network, GameSession session) {
            super(network);
            this.session = session;
        }
    }

    /**
     * Fired when a {@link org.obsidianbox.ember.network.GameSession} has been inactivated.
     */
    public static class SessionInactivated extends NetworkEvent {
        public final GameSession session;

        public SessionInactivated(Network network, GameSession session) {
            super(network);
            this.session = session;
        }
    }
}

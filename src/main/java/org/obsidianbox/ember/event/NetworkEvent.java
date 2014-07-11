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
package org.obsidianbox.ember.event;

import org.obsidianbox.ember.Game;
import org.obsidianbox.ember.network.GameProtocol;
import org.obsidianbox.ember.network.GameSession;
import io.netty.channel.Channel;

public abstract class NetworkEvent extends GameEvent {
    public NetworkEvent(Game game) {
        super(game);
    }

    /**
     * Fired when a new {@link org.obsidianbox.ember.network.GameSession} is about to be created.
     * <p>
     * Use this to set the {@link org.obsidianbox.ember.network.GameProtocol} for the session being created.
     */
    public static class PreSessionCreate extends NetworkEvent {
        public final Channel channel;
        public GameProtocol protocol;

        public PreSessionCreate(Game game, Channel channel) {
            super(game);
            this.channel = channel;
        }
    }

    /**
     * Fired when a new {@link org.obsidianbox.ember.network.GameSession} has been created.
     */
    public static class PostSessionCreate extends NetworkEvent {
        public final GameSession session;

        public PostSessionCreate(Game game, GameSession session) {
            super(game);
            this.session = session;
        }
    }

    /**
     * Fired when a {@link org.obsidianbox.ember.network.GameSession} is ready for {@link com.flowpowered.networking.Message}s.
     */
    public static class SessionReady extends NetworkEvent {
        public final GameSession session;

        public SessionReady(Game game, GameSession session) {
            super(game);
            this.session = session;
        }
    }

    /**
     * Fired when a {@link org.obsidianbox.ember.network.GameSession} is being disconnected.
     */
    public static class SessionDisconnect extends NetworkEvent {
        public final GameSession session;

        public SessionDisconnect(Game game, GameSession session) {
            super(game);
            this.session = session;
        }
    }

    /**
     * Fired when a {@link org.obsidianbox.ember.network.GameSession} has been inactivated.
     */
    public static class SessionInactivated extends NetworkEvent {
        public final GameSession session;

        public SessionInactivated(Game game, GameSession session) {
            super(game);
            this.session = session;
        }
    }
}

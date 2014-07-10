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
package com.obsidianbox.ember.network;

import com.flowpowered.networking.NetworkServer;
import com.flowpowered.networking.session.Session;
import com.obsidianbox.ember.Game;
import com.obsidianbox.ember.event.NetworkEvent;
import io.netty.channel.Channel;

public class GameNetworkServer extends NetworkServer {
    private final Game game;

    public GameNetworkServer(Game game) {
        this.game = game;
    }

    @Override
    public Session newSession(Channel c) {
        final GameProtocol protocol = game.getEventManager().callEvent(new NetworkEvent.PreSessionCreate(game, c)).protocol;
        if (protocol == null) {
            c.disconnect();
        }
        final GameSession session = new GameSession(c, protocol);
        return game.getEventManager().callEvent(new NetworkEvent.PostSessionCreate(game, session)).session;
    }

    @Override
    public void sessionInactivated(Session session) {
        game.getEventManager().callEvent(new NetworkEvent.SessionInactivated(game, (GameSession) session));
    }
}

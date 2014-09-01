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
package org.obsidianbox.ember.network;

import java.net.SocketAddress;
import java.util.HashSet;
import java.util.Set;

import com.flowpowered.networking.session.BasicSession;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import com.flowpowered.networking.NetworkServer;
import com.flowpowered.networking.session.Session;

import org.obsidianbox.ember.event.NetworkEvent;

public final class GameNetworkServer extends NetworkServer {
    private final Network network;
    protected final Set<GameSession> sessions = new HashSet<>();

    public GameNetworkServer(Network network) {
        this.network = network;
    }

    @Override
    public Session newSession(Channel c) {
        final GameProtocol protocol = network.game.getEventManager().callEvent(new NetworkEvent.PreSessionCreate(network, c)).protocol;
        if (protocol == null) {
            network.game.logger.error("No protocol provided for channel [" + c + "], disconnecting...");
            c.disconnect();
        }
        final GameSession session = new GameSession(network, c, protocol);
        sessions.add(session);
        return network.game.getEventManager().callEvent(new NetworkEvent.PostSessionCreate(network, session)).session;
    }

    @Override
    public void sessionInactivated(Session session) {
        network.game.getEventManager().callEvent(new NetworkEvent.SessionInactivated(network, (GameSession) session));
        sessions.remove(session);
    }

    @Override
    public ChannelFuture bind(SocketAddress address) {
        if (!network.isRunning()) {
            throw new IllegalStateException("Attempt made to bind network to address [" + address + "] but network is not running!");
        }
        return super.bind(address);
    }

    @Override
    public void onBindSuccess(SocketAddress address) {
        network.game.logger.info("Bound to address [" + address + "]. Ready for connections...");
    }

    @Override
    public void onBindFailure(SocketAddress address, Throwable t) {
        network.game.logger.info("Exception caught while binding to address [" + address + "]. Do you have another instance of Ember running?", t);
    }

    @Override
    public void shutdown() {
        if (!network.isRunning()) {
            throw new IllegalStateException("Attempt made to shutdown binding adapter but network is not running!");
        }
        sessions.stream().filter(BasicSession::isActive).forEach(BasicSession::disconnect);
        super.shutdown();
    }
}
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
package org.obsidianbox.ember.game.network;

import com.flowpowered.networking.NetworkServer;
import com.flowpowered.networking.session.BasicSession;
import com.flowpowered.networking.session.Session;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.obsidianbox.ember.game.Ember;
import org.obsidianbox.ember.game.event.NetworkEvent;

import java.net.SocketAddress;
import java.util.HashSet;
import java.util.Set;

public final class GameNetworkServer extends NetworkServer {

    protected final Set<GameSession> sessions = new HashSet<>();
    private final Network network;

    public GameNetworkServer(Network network) {
        this.network = network;
    }

    @Override
    public Session newSession(Channel c) {
        GameProtocol protocol = network.game.getEventManager().callEvent(new NetworkEvent.PreSessionCreate(network, c)).protocol;
        if (protocol == null) {
            protocol = network.nullProtocol;
            c.disconnect().addListener(future -> {
                Ember.LOGGER.error("No plugin provided a suitable protocol for channel [" + c + "] and therefore has been closed");
            });
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
            Ember.LOGGER.warn("Binding to address " + address + " but network thread isn't running. No messages will be processed!");
        }
        return super.bind(address);
    }

    @Override
    public void onBindSuccess(SocketAddress address) {
        Ember.LOGGER.info("Bound to address [" + address + "]. Awaiting connections...");
    }

    @Override
    public void onBindFailure(SocketAddress address, Throwable t) {
        Ember.LOGGER.info("Exception caught while binding to address [" + address + "]. Do you have another instance of Ember running?", t);
    }

    @Override
    public void shutdown() {
        sessions.stream().filter(BasicSession::isActive).forEach(BasicSession::disconnect);
        super.shutdown();
    }
}
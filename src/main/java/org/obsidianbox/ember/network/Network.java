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
package org.obsidianbox.ember.network;

import com.flowpowered.commons.ticking.TickingElement;
import com.flowpowered.networking.NetworkClient;
import com.flowpowered.networking.NetworkServer;
import com.flowpowered.networking.session.BasicSession;
import com.flowpowered.networking.session.PulsingSession;
import com.flowpowered.networking.session.Session;
import io.netty.channel.ChannelFuture;
import org.obsidianbox.ember.Game;
import org.obsidianbox.ember.event.NetworkEvent;
import io.netty.channel.Channel;

import java.net.SocketAddress;
import java.util.HashSet;
import java.util.Set;

public class Network extends TickingElement {
    protected final Game game;
    protected final GameNetworkServer server;
    protected final Set<GameSession> activeSessions = new HashSet<>();

    //Receiving messages
    protected final GameNetworkClient client;
    protected GameSession listener;

    public Network(Game game) {
        super("Network", 20);
        this.game = game;
        server = new GameNetworkServer(this);
        client = new GameNetworkClient(this);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onTick(long dt) {
        if (listener != null) {
            listener.pulse();
        }
        activeSessions.stream().filter(BasicSession::isActive).forEach(PulsingSession::pulse);
    }

    @Override
    public void onStop() {

    }

    public ChannelFuture bind(SocketAddress address) {
        if (!isRunning()) {
            throw new IllegalStateException("Attempt made to bind network to address [" + address + "] but network is not running!");
        }
        return server.bind(address);
    }

    public ChannelFuture connect(SocketAddress address) {
        if (!isRunning()) {
            throw new IllegalStateException("Attempt made to connect to address [" + address + "] but network is not running!");
        }
        return client.connect(address);
    }
}

final class GameNetworkServer extends NetworkServer {
    private final Network network;

    public GameNetworkServer(Network network) {
        this.network = network;
    }

    @Override
    public Session newSession(Channel c) {
        final GameProtocol protocol = network.game.getEventManager().callEvent(new NetworkEvent.PreSessionCreate(network.game, c)).protocol;
        if (protocol == null) {
            network.game.logger.error("No protocol provided for channel [" + c + "], disconnecting...");
            c.disconnect();
        }
        final GameSession session = new GameSession(network.game, c, protocol);
        network.activeSessions.add(session);
        return network.game.getEventManager().callEvent(new NetworkEvent.PostSessionCreate(network.game, session)).session;
    }

    @Override
    public void sessionInactivated(Session session) {
        network.game.getEventManager().callEvent(new NetworkEvent.SessionInactivated(network.game, (GameSession) session));
        network.activeSessions.remove(session);
    }
}


final class GameNetworkClient extends NetworkClient {
    private final Network network;

    public GameNetworkClient(Network network) {
        this.network = network;
    }

    @Override
    public Session newSession(Channel c) {
        final GameProtocol protocol = network.game.getEventManager().callEvent(new NetworkEvent.PreSessionCreate(network.game, c)).protocol;
        if (protocol == null) {
            network.game.logger.error("No protocol provided for channel [" + c + "], disconnecting...");
            c.disconnect();
        }
        network.listener = new GameSession(network.game, c, protocol);
        return network.game.getEventManager().callEvent(new NetworkEvent.PostSessionCreate(network.game, network.listener)).session;
    }

    @Override
    public void sessionInactivated(Session session) {
        network.game.getEventManager().callEvent(new NetworkEvent.SessionInactivated(network.game, (GameSession) session));
        network.listener = null;
    }
}
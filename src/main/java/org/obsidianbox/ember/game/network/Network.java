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

import com.flowpowered.commons.ticking.TickingElement;
import com.flowpowered.networking.session.BasicSession;
import com.flowpowered.networking.session.PulsingSession;
import io.netty.channel.ChannelFuture;
import org.obsidianbox.ember.game.Ember;
import org.obsidianbox.ember.game.network.protocol.NullProtocol;

import java.net.SocketAddress;

public class Network extends TickingElement {

    public final Ember game;
    protected final GameNetworkClient client;
    protected final GameNetworkServer server;
    protected final NullProtocol nullProtocol;

    public Network(Ember game) {
        super("network", 20);
        this.game = game;
        server = new GameNetworkServer(this);
        client = new GameNetworkClient(this);
        nullProtocol = new NullProtocol(game);
    }

    @Override
    public void onStart() {
        Ember.LOGGER.info("Starting network");
    }

    @Override
    public void onTick(long dt) {
        if (client.session != null) {
            client.session.pulse();
        }
        server.sessions.stream().filter(BasicSession::isActive).forEach(PulsingSession::pulse);
    }

    @Override
    public void onStop() {
        Ember.LOGGER.info("Stopping network");
        client.shutdown();
        server.shutdown();
    }

    public void disconnect() {
        client.shutdown();
    }

    public ChannelFuture connect(SocketAddress address) {
        if (!isRunning()) {
            start();
        }
        return client.connect(address);
    }

    public ChannelFuture bind(SocketAddress address) {
        if (!isRunning()) {
            start();
        }
        return server.bind(address);
    }

    public void unbind() {
        server.shutdown();
    }
}

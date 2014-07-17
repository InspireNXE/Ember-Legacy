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

import com.flowpowered.networking.NetworkClient;
import com.flowpowered.networking.session.Session;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.obsidianbox.ember.event.NetworkEvent;

import java.net.SocketAddress;

public final class GameNetworkClient extends NetworkClient {
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

    @Override
    public ChannelFuture connect(SocketAddress address) {
        if (!network.isRunning()) {
            throw new IllegalStateException("Attempt made to connect to address [" + address + "] but network is not running!");
        }
        return super.connect(address);
    }

    @Override
    public void onConnectSuccess(SocketAddress address) {
        network.game.logger.info("Connected to address [" + address + "]");
    }

    @Override
    public void onConnectFailure(SocketAddress address, Throwable t) {
        network.game.logger.error("Exception caught while connecting to address [" + address + "]", t);
    }
}
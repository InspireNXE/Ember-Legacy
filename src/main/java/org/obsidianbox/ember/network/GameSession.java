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

import io.netty.channel.Channel;

import com.flowpowered.networking.Message;
import com.flowpowered.networking.MessageHandler;
import com.flowpowered.networking.protocol.AbstractProtocol;
import com.flowpowered.networking.session.PulsingSession;

import org.obsidianbox.ember.Game;
import org.obsidianbox.ember.event.NetworkEvent;

public final class GameSession extends PulsingSession {
    private final Network network;

    public GameSession(Network network, Channel channel, AbstractProtocol protocol) {
        super(channel, protocol);
        this.network = network;
    }

    @Override
    public void disconnect() {
        network.game.getEventManager().callEvent(new NetworkEvent.PreSessionDisconnect(network, this));
        super.disconnect();
    }

    @Override
    public void onDisconnect() {
        network.game.getEventManager().callEvent(new NetworkEvent.PostSessionDisconnect(network, this));
    }

    @Override
    public void onReady() {
        network.game.getEventManager().callEvent(new NetworkEvent.SessionReady(network, this));
    }

    @Override
    public void onInboundThrowable(Throwable throwable) {
        getLogger().warn("Exception caught inbound for session [" + this + "]", throwable);
    }

    @Override
    public void onOutboundThrowable(Throwable throwable) {
        getLogger().warn("Exception caught outbound for session [" + this + "]", throwable);
    }

    @Override
    public void onHandlerThrowable(Message message, MessageHandler<?, ?> handle, Throwable throwable) {
        getLogger().warn("Exception caught handling message [" + message + "] using handler [" + handle + "]", throwable);
    }
}

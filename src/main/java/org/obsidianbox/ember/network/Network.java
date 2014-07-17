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

import java.util.HashSet;
import java.util.Set;

import com.flowpowered.commons.ticking.TickingElement;
import com.flowpowered.networking.session.BasicSession;
import com.flowpowered.networking.session.PulsingSession;

import org.obsidianbox.ember.Game;

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
        game.logger.info("Starting network");
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
        game.logger.info("Stopping network");
        if (listener != null) {
            listener.disconnect();
        }
        client.shutdown();
        activeSessions.stream().filter(BasicSession::isActive).forEach(BasicSession::disconnect);
        server.shutdown();
    }

    public GameNetworkClient getListeningAdapter() {
        return client;
    }

    public GameNetworkServer getBindingAdapter() {
        return server;
    }
}

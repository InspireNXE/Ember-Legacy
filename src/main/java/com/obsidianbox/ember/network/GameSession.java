package com.obsidianbox.ember.network;

import com.flowpowered.networking.protocol.AbstractProtocol;
import com.flowpowered.networking.session.PulsingSession;
import io.netty.channel.Channel;

public class GameSession extends PulsingSession {
    public GameSession(Channel channel, AbstractProtocol protocol) {
        super(channel, protocol);
    }
}

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
        final GameProtocol protocol = game.getEventManager().callEvent(new NetworkEvent.SessionCreatePreEvent(game, c)).protocol;
        if (protocol == null) {
            c.disconnect();
        }
        final GameSession session = new GameSession(c, protocol);
        return game.getEventManager().callEvent(new NetworkEvent.SessionCreatePostEvent(game, session)).session;
    }

    @Override
    public void sessionInactivated(Session session) {
        game.getEventManager().callEvent(new NetworkEvent.SessionInactivatedEvent(game, (GameSession) session));
    }
}

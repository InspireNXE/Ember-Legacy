package org.obsidianbox.ember.network;

import com.flowpowered.networking.NetworkServer;
import com.flowpowered.networking.session.Session;
import org.obsidianbox.ember.Game;
import org.obsidianbox.ember.event.NetworkEvent;
import io.netty.channel.Channel;

import java.net.SocketAddress;
import java.util.HashSet;
import java.util.Set;

public class Network {
    private final Game game;
    private final GameNetworkServer server;
    private final Set<GameSession> sessions = new HashSet<>();

    public Network(Game game) {
        this.game = game;
        server = new GameNetworkServer(game);
    }

    public void bind(SocketAddress address) {
        server.bind(address);
    }
}

final class GameNetworkServer extends NetworkServer {
    private final Game game;

    public GameNetworkServer(Game game) {
        this.game = game;
    }

    @Override
    public Session newSession(Channel c) {
        final GameProtocol protocol = game.getEventManager().callEvent(new NetworkEvent.PreSessionCreate(game, c)).protocol;
        if (protocol == null) {
            game.logger.error("No protocol provided for channel [" + c + "], disconnecting...");
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
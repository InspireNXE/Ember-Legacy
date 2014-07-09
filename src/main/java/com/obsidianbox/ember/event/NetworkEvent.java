package com.obsidianbox.ember.event;

import com.obsidianbox.ember.Game;
import com.obsidianbox.ember.network.GameSession;
import com.obsidianbox.ember.network.GameProtocol;
import io.netty.channel.Channel;

public abstract class NetworkEvent extends GameEvent {
    public NetworkEvent(Game game) {
        super(game);
    }

    /**
     * Fired when a new {@link com.obsidianbox.ember.network.GameSession} is about to be created.
     *
     * Use this to set the {@link com.obsidianbox.ember.network.GameProtocol} for the session being created.
     */
    public static class SessionCreatePreEvent extends NetworkEvent {
        public final Channel channel;
        public GameProtocol protocol;

        public SessionCreatePreEvent(Game game, Channel channel) {
            super(game);
            this.channel = channel;
        }
    }

    /**
     * Fired when a new {@link com.obsidianbox.ember.network.GameSession} has been created.
     */
    public static class SessionCreatePostEvent extends NetworkEvent {
        public final GameSession session;

        public SessionCreatePostEvent(Game game, GameSession session) {
            super(game);
            this.session = session;
        }
    }

    /**
     * Fired when a {@link com.obsidianbox.ember.network.GameSession} has been inactivated.
     */
    public static class SessionInactivatedEvent extends NetworkEvent {
        public final GameSession session;

        public SessionInactivatedEvent(Game game, GameSession session) {
            super(game);
            this.session = session;
        }
    }
}

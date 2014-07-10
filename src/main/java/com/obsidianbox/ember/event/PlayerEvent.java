package com.obsidianbox.ember.event;

import com.flowpowered.chat.ChatReceiver;
import com.flowpowered.permissions.PermissionDomain;
import com.obsidianbox.ember.Game;
import com.obsidianbox.ember.physics.Player;

public abstract class PlayerEvent extends EntityEvent {
    public PlayerEvent(Game game, Player player) {
        super(game, player);
    }

    /**
     * Fired when a {@link com.obsidianbox.ember.physics.Player} invokes a permission check.
     *
     * Calling {@link com.obsidianbox.ember.event.PlayerEvent#setCancelled(boolean)} and passing in true will result in
     * the permission being denied.
     */
    public static class PermissionEvent extends PlayerEvent {
        public final String permission;
        public final PermissionDomain domain;

        public PermissionEvent(Game game, Player player, String permission) {
            this(game, player, null, permission);
        }

        public PermissionEvent(Game game, Player player, PermissionDomain domain, String permission) {
            super(game, player);
            this.domain = domain;
            this.permission = permission;
        }

        @Override
        public void setCancelled(boolean cancelled) {
            super.setCancelled(cancelled);
        }
    }
}

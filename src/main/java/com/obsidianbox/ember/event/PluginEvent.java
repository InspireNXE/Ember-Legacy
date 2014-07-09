package com.obsidianbox.ember.event;

import com.obsidianbox.ember.Game;

public class PluginEvent extends GameEvent {
    public PluginEvent(Game game) {
        super(game);
    }

    public static class PluginLoadEvent extends PluginEvent {
        public PluginLoadEvent(Game game) {
            super(game);
        }
    }

    public static class PluginEnableEvent extends PluginEvent {
        public PluginEnableEvent(Game game) {
            super(game);
        }
    }

    public static class PluginReloadEvent extends PluginEvent {
        public PluginReloadEvent(Game game) {
            super(game);
        }
    }

    public static class PluginDisableEvent extends PluginEvent {
        public PluginDisableEvent(Game game) {
            super(game);
        }
    }
}

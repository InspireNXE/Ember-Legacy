package org.obsidianbox.ember.event;

import org.obsidianbox.ember.Game;
import org.obsidianbox.ember.component.Component;

public abstract class ComponentEvent extends GameEvent {
    public final Component<?> component;

    public ComponentEvent(Game game, Component<?> component) {
        super(game);
        this.component = component;
    }

    /**
     * Fired when the {@link org.obsidianbox.ember.component.Component} is attached to a {@link org.obsidianbox.ember.component.IComponentHolder}.
     */
    public static class Attached extends ComponentEvent {
        public Attached(Game game, Component<?> component) {
            super(game, component);
        }
    }

    /**
     * Fired when the {@link org.obsidianbox.ember.component.Component} is about to be detached from its {@link org.obsidianbox.ember.component.IComponentHolder}.
     */
    public static class Detach extends ComponentEvent {
        public Detach(Game game, Component<?> component) {
            super(game, component);
        }
    }
}

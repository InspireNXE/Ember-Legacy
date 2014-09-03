package org.obsidianbox.ember.event;

import org.obsidianbox.ember.Game;
import org.obsidianbox.ember.component.IComponent;

public abstract class ComponentEvent extends GameEvent {
    public final IComponent<?> component;

    public ComponentEvent(Game game, IComponent<?> component) {
        super(game);
        this.component = component;
    }

    /**
     * Fired when the {@link org.obsidianbox.ember.component.IComponent} is attached to a {@link org.obsidianbox.ember.component.IComponentHolder}.
     */
    public static class Attached extends ComponentEvent {
        public Attached(Game game, IComponent<?> component) {
            super(game, component);
        }
    }

    /**
     * Fired when the {@link org.obsidianbox.ember.component.IComponent} is about to be detached from its {@link org.obsidianbox.ember.component.IComponentHolder}.
     */
    public static class Detach extends ComponentEvent {
        public Detach(Game game, IComponent<?> component) {
            super(game, component);
        }
    }
}

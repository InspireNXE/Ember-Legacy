package org.obsidianbox.ember.event;

import org.obsidianbox.ember.Game;
import org.obsidianbox.ember.universe.World;

public abstract class WorldEvent extends GameEvent {
    public final World world;

    public WorldEvent(Game game, World world) {
        super(game);
        this.world = world;
    }

    public static class Create extends WorldEvent {
        public Create(Game game, World world) {
            super(game, world);
        }
    }

    public static class Load extends WorldEvent{
        public Load(Game game, World world) {
            super(game, world);
        }
    }
}

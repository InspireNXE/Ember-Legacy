package org.obsidianbox.ember.event;

import org.obsidianbox.ember.Game;
import org.obsidianbox.ember.universe.World;
import org.obsidianbox.ember.universe.cuboid.Chunk;

public abstract class ChunkEvent extends WorldEvent {
    public final Chunk chunk;

    public ChunkEvent(Game game, World world, Chunk chunk) {
        super(game, world);
        this.chunk = chunk;
    }

    public static class Load extends ChunkEvent {
        public Load(Game game, World world, Chunk chunk) {
            super(game, world, chunk);
        }
    }

    public static class Unload extends ChunkEvent {
        public Unload(Game game, World world, Chunk chunk) {
            super(game, world, chunk);
        }
    }
}

package org.obsidianbox.ember.universe.generator;

import org.obsidianbox.ember.Game;
import org.obsidianbox.ember.universe.World;
import org.obsidianbox.ember.universe.cuboid.Chunk;

public interface WorldGenerator {
    void generate(Game game, World world, Chunk chunk);
}

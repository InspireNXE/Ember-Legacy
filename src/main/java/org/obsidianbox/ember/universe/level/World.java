/**
 * This file is part of Ember, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014 ObsidianBox <http://obsidianbox.org/>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.obsidianbox.ember.universe.level;

import com.flowpowered.commons.hashing.Int21TripleHashed;
import com.flowpowered.commons.ticking.TickingElement;
import com.flowpowered.plugins.Plugin;
import org.obsidianbox.ember.Game;
import org.obsidianbox.ember.event.ChunkEvent;
import org.obsidianbox.ember.universe.generator.WorldGenerator;
import org.obsidianbox.ember.universe.material.IMaterial;
import org.obsidianbox.ember.util.LongObjectHashMap;
import org.spout.physics.body.RigidBody;
import org.spout.physics.engine.linked.LinkedDynamicsWorld;
import org.spout.physics.engine.linked.LinkedWorldInfo;
import org.spout.physics.math.Vector3;

import java.util.Optional;
import java.util.UUID;

public final class World extends TickingElement {

    public final Game game;
    public final Plugin plugin;
    public final String identifier;
    public final UUID uuid;
    public final LinkedDynamicsWorld physics;
    private final WorldGenerator generator;
    private final LongObjectHashMap<Chunk> chunks = new LongObjectHashMap<>();

    public World(Game game, Plugin plugin, String identifier, WorldGenerator generator) {
        this(game, plugin, identifier, generator, UUID.randomUUID());
    }

    protected World(Game game, Plugin plugin, String identifier, WorldGenerator generator, UUID uuid) {
        super("world [" + identifier + "]", 20);
        this.game = game;
        this.plugin = plugin;
        this.identifier = identifier;
        this.generator = generator;
        this.uuid = uuid;
        physics = new LinkedDynamicsWorld(new Vector3(0, -9.8f, 0), new PhysicsWorldInfo(this));
    }

    @Override
    public void onStart() {
        physics.start();
    }

    @Override
    public void onTick(long l) {
        physics.update();
    }

    @Override
    public void onStop() {
        physics.stop();
    }

    public Optional<Chunk> getChunk(int vx, int vy, int vz, LoadOption option) {
        final int cx = vx << Chunk.BITS.BITS;
        final int cy = vy << Chunk.BITS.BITS;
        final int cz = vz << Chunk.BITS.BITS;
        return getChunkWithOptions(cx, cy, cz, option);
    }

    public Optional<Chunk> getChunkExact(int cx, int cy, int cz, LoadOption option) {
        return getChunkWithOptions(cx, cy, cz, option);
    }

    private Optional<Chunk> getChunkWithOptions(int cx, int cy, int cz, LoadOption option) {
        final long key = Int21TripleHashed.key(cx, cy, cz);
        Chunk chunk = chunks.get(key);
        switch (option) {
            case NO_LOAD:
                break;
            case LOAD_ONLY:
                if (chunk == null) {
                    final Location location = new Location(this, cx, cy, cz);
                    chunk = new Chunk(location);
                    location.setChunkReference(chunk);
                    chunks.put(key, chunk);
                    game.getEventManager().callEvent(new ChunkEvent.Load(game, this, chunk));
                }
                break;
            case GENERATE_ONLY:
                if (chunk != null) {
                    generator.generate(game, this, chunk);
                }
                break;
            case LOAD_AND_GENERATE:
                if (chunk == null) {
                    final Location location = new Location(this, cx, cy, cz);
                    chunk = new Chunk(location);
                    location.setChunkReference(chunk);
                    chunks.put(key, chunk);
                    game.getEventManager().callEvent(new ChunkEvent.Load(game, this, chunk));
                }
                generator.generate(game, this, chunk);
                break;
        }
        return Optional.ofNullable(chunk);
    }

    @Override
    public boolean equals(Object o) {
        return this == o || !(o == null || getClass() != o.getClass()) && uuid.equals(((World) o).uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    private final class PhysicsWorldInfo implements LinkedWorldInfo {

        private final World world;

        public PhysicsWorldInfo(World world) {
            this.world = world;
        }

        @Override
        public RigidBody getBody(int vx, int vy, int vz) {
            final long key = Int21TripleHashed.key(vx << Chunk.BITS.BITS, vy << Chunk.BITS.BITS, vz << Chunk.BITS.BITS);
            final Chunk chunk = chunks.get(key);
            final Optional<IMaterial> material = chunk != null ? chunk.getMaterial(vx, vy, vz) : Optional.empty();
            if (material.isPresent()) {
                return material.get().getBody(world, vx, vy, vz);
            }
            return null;
        }
    }
}

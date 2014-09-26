package org.obsidianbox.ember.universe.cuboid;

import org.obsidianbox.ember.universe.Location;

import java.lang.ref.WeakReference;

public final class ChunkReference extends WeakReference<Chunk> {
    private final Location location;

    public ChunkReference(Chunk referent) {
        super(referent);
        location = referent.location;
    }

    @Override
    public boolean equals(Object o) {
        return this == o || !(o == null || getClass() != o.getClass()) && location.equals(((ChunkReference) o).location);
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }
}

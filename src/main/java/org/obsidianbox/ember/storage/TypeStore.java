package org.obsidianbox.ember.storage;

import com.flowpowered.math.vector.Vector3f;
import com.flowpowered.math.vector.Vector3i;

import java.util.Optional;

/**
 * Stores a {@link T} at a x, y, z
 */
public interface TypeStore<T> {
    public Optional<T> get(int x, int y, int z);

    public Optional<T> get(Vector3i position);

    public Optional<T> get(Vector3f position);

    public Optional<T> getAndSet(int x, int y, int z, T value);

    public Optional<T> getAndSet(Vector3i position, T value);

    public Optional<T> getAndSet(Vector3f position, T value);

    public boolean set(int x, int y, int z, T value);

    public boolean set(Vector3i position, T value);

    public boolean set(Vector3f position, T value);

    public boolean isUniform();

    public boolean isDirty();
}

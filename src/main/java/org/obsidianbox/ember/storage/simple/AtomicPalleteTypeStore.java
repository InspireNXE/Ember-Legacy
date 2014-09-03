package org.obsidianbox.ember.storage.simple;

import com.flowpowered.math.vector.Vector3f;
import com.flowpowered.math.vector.Vector3i;
import org.obsidianbox.ember.storage.TypeStore;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicPalleteTypeStore<T> implements TypeStore<T> {
    private final ConcurrentMap<T, Integer> typeToIntMap;
    private final AtomicInteger counter = new AtomicInteger(0);
    private final AtomicIntegerArray store;

    // Coordinate bounds
    private final int cuboidSize;
    private final Vector3i cuboidCenter;
    private final int voxelCenterX;
    private final int voxelCenterY;
    private final int voxelCenterZ;
    private final int voxelMinX;
    private final int voxelMinY;
    private final int voxelMinZ;
    private final int voxelMaxX;
    private final int voxelMaxY;
    private final int voxelMaxZ;

    public AtomicPalleteTypeStore(Vector3i cuboidCenter, int cuboidSize) {
        this.cuboidCenter = cuboidCenter;
        this.cuboidSize = cuboidSize;
        typeToIntMap = new ConcurrentHashMap<>();
        store = new AtomicIntegerArray(cuboidSize * 3);

        // Figure out our bounds
        voxelCenterX = cuboidCenter.getX() >> cuboidSize;
        voxelCenterY = cuboidCenter.getY() >> cuboidSize;
        voxelCenterZ = cuboidCenter.getZ() >> cuboidSize;
        voxelMinX = voxelCenterX - cuboidSize / 2;
        voxelMinY = voxelCenterY - cuboidSize / 2;
        voxelMinZ = voxelCenterZ - cuboidSize / 2;
        voxelMaxX = voxelCenterX + cuboidSize / 2;
        voxelMaxY = voxelCenterX + cuboidSize / 2;
        voxelMaxZ = voxelCenterX + cuboidSize / 2;
    }

    @Override
    public Optional<T> get(int x, int y, int z) {
        if (x < voxelMinX || x > voxelMaxX || y < voxelMinY || y > voxelMaxY || z < voxelMinZ || z > voxelMaxZ) {
            return Optional.empty();
        }
        final int rawId = store.get(x + (y * cuboidSize) + (z * (cuboidSize * cuboidSize)));
        return typeToIntMap.entrySet().stream().filter(en -> en.getValue() == rawId).map(Map.Entry::getKey).findAny();
    }

    @Override
    public Optional<T> get(Vector3i position) {
        return null;
    }

    @Override
    public Optional<T> get(Vector3f position) {
        return null;
    }

    @Override
    public Optional<T> getAndSet(int x, int y, int z, T value) {
        return null;
    }

    @Override
    public Optional<T> getAndSet(Vector3i position, T value) {
        return null;
    }

    @Override
    public Optional<T> getAndSet(Vector3f position, T value) {
        return null;
    }

    @Override
    public boolean set(int x, int y, int z, T value) {
        if (x < voxelMinX || x > voxelMaxX || y < voxelMinY || y > voxelMaxY || z < voxelMinZ || z > voxelMaxZ) {
            return false;
        }

        Integer mapped = typeToIntMap.get(value);
        if (mapped == null) {
            mapped = counter.getAndIncrement();
            typeToIntMap.put(value, mapped);
        }
        store.set(x + (y * cuboidSize) + (z * (cuboidSize * cuboidSize)), mapped);
        return true;
    }

    @Override
    public boolean set(Vector3i position, T value) {
        return false;
    }

    @Override
    public boolean set(Vector3f position, T value) {
        return false;
    }

    @Override
    public boolean isUniform() {
        return false;
    }

    @Override
    public boolean isDirty() {
        return false;
    }
}

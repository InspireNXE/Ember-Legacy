package org.obsidianbox.ember.universe;

public final class Location {
    public final World world;
    public final int x, y, z;

    public Location(World world, int x, int y, int z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Location location = (Location) o;

        return x == location.x && y == location.y && z == location.z && world.equals(location.world);
    }

    @Override
    public int hashCode() {
        int result = world.hashCode();
        result = 31 * result + x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }
}

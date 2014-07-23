package org.obsidianbox.ember;

import com.flowpowered.math.vector.Vector3i;
import org.junit.Test;
import org.obsidianbox.ember.world.World;
import org.obsidianbox.ember.world.cuboid.Chunk;
import org.obsidianbox.ember.world.voxel.Material;
import org.obsidianbox.ember.world.voxel.Voxel;

import static org.junit.Assert.assertTrue;

public class WorldTest {
    private final World world = new World("testWorld");

    @Test
    public void testVoxel() {
        final Voxel a = new Voxel(new Vector3i(0, 0, 0), Material.AIR);
        assertTrue(a.position.equals(new Vector3i(0, 0, 0)));
        assertTrue(a.material().equals(Material.AIR));
    }

    @Test
    public void testChunk() {
        for (int i = 0; i < 20000; i++) {
            new Chunk(world, new Vector3i(i++, i++, i++));
        }
    }
}

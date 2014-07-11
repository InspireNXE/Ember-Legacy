package org.obsidianbox.ember.physics.util;

import com.flowpowered.math.imaginary.Quaternionf;
import com.flowpowered.math.vector.Vector3f;
import org.obsidianbox.ember.universe.World;

public class Transform {
    private World world;
    private Vector3f position;
    private Quaternionf rotation = Quaternionf.IDENTITY;
    private Vector3f scale = Vector3f.ONE;

    public Transform(World world, Vector3f position) {
        this.world = world;
        this.position = position;
    }

    public Transform(World world, Vector3f position, Quaternionf rotation, Vector3f scale) {
        this.world = world;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public void setRotation(Quaternionf rotation) {
        this.rotation = rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }
}

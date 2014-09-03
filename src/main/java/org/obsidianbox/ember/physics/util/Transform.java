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
package org.obsidianbox.ember.physics.util;

import com.flowpowered.math.imaginary.Quaternionf;
import com.flowpowered.math.vector.Vector3f;
import org.obsidianbox.ember.world.World;

import java.util.Optional;

public class Transform {
    public static final Transform NONE = new Transform();

    private Optional<World> world = Optional.empty();
    private Optional<Vector3f> position = Optional.empty();
    private Optional<Quaternionf> rotation = Optional.empty();
    private Optional<Vector3f> scale = Optional.empty();

    public Transform() {
    }

    public Transform(World world) {
        this(world, null, null, null);
    }

    public Transform(World world, Vector3f position) {
        this(world, position, null, null);
    }

    public Transform(World world, Vector3f position, Quaternionf rotation, Vector3f scale) {
        this.world = Optional.ofNullable(world);
        this.position = Optional.ofNullable(position);
        this.rotation = Optional.ofNullable(rotation);
        this.scale = Optional.ofNullable(scale);
    }

    public Optional<World> getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = Optional.ofNullable(world);
    }

    public Optional<Vector3f> getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = Optional.ofNullable(position);
    }

    public Optional<Quaternionf> getRotation() {
        return rotation;
    }

    public void setRotation(Quaternionf rotation) {
        this.rotation = Optional.ofNullable(rotation);
    }

    public Optional<Vector3f> getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = Optional.ofNullable(scale);
    }
}

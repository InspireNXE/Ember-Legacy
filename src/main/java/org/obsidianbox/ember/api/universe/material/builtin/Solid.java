/**
 * This file is part of Ember, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014-2015 InspireNXE <http://inspirenxe.org/>
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
package org.obsidianbox.ember.api.universe.material.builtin;

import org.obsidianbox.ember.api.universe.level.World;
import org.obsidianbox.ember.api.universe.material.Material;
import org.spout.physics.body.RigidBody;
import org.spout.physics.collision.shape.BoxShape;
import org.spout.physics.math.Quaternion;
import org.spout.physics.math.Transform;
import org.spout.physics.math.Vector3;

public class Solid implements Material {

    public static final BoxShape PHYSICS_CUBE_SHAPE = new BoxShape(1f, 1f, 1f);
    public static final float MASS = 1f;
    private static RigidBody body;

    @Override
    public String getName() {
        return "solid";
    }

    @Override
    public RigidBody getBody(World world, int vx, int vy, int vz) {
        if (body == null) {
            body = world.getPhysics().createRigidBody(new Transform(new Vector3(vx, vy, vz), Quaternion.identity()), MASS, PHYSICS_CUBE_SHAPE);
        } else {
            body.getTransform().setPosition(new Vector3(vx, vy, vz));
        }
        return body;
    }
}

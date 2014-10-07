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
package org.obsidianbox.ember;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.obsidianbox.ember.api.universe.level.World;
import org.obsidianbox.ember.api.universe.material.Material;
import org.obsidianbox.ember.game.universe.material.MaterialStoreImpl;
import org.obsidianbox.ember.api.universe.material.MaterialRegistrationException;
import org.spout.physics.body.RigidBody;

public class WorldTest {

    private final MaterialStoreImpl materialStore = new MaterialStoreImpl();

    @Test
    public void testMaterialManager() {
        assertFalse(materialStore.get((String) null).isPresent());
        assertTrue(materialStore.get("none").isPresent());
        try {
            materialStore.register(new TestMaterial());
        } catch (MaterialRegistrationException e) {
            fail("Material registration failed!");
        }

        boolean failed = false;
        try {
            materialStore.register(null);
        } catch (MaterialRegistrationException e) {
            failed = true;
        }
        if (!failed) {
            fail("Registering a null material should have failed!");
        }

        failed = false;
        try {
            materialStore.register(new Material() {
                @Override
                public String getName() {
                    return null;
                }

                @Override
                public RigidBody getBody(World world, int vx, int vy, int vz) {
                    return null;
                }
            });
        } catch (MaterialRegistrationException e) {
            failed = true;
        }
        if (!failed) {
            fail("Registering a null material name should have failed!");
        }

        failed = false;
        try {
            materialStore.register(new Material() {
                @Override
                public String getName() {
                    return "";
                }

                @Override
                public RigidBody getBody(World world, int vx, int vy, int vz) {
                    return null;
                }
            });
        } catch (MaterialRegistrationException e) {
            failed = true;
        }
        if (!failed) {
            fail("Registering a material with an empty name should have failed!");
        }

        assertFalse(materialStore.NONE.getName().equals(materialStore.get("test").get().getName()));
    }

    private final class TestMaterial implements Material {

        @Override
        public String getName() {
            return "test";
        }

        @Override
        public RigidBody getBody(World world, int vx, int vy, int vz) {
            return null;
        }
    }
}

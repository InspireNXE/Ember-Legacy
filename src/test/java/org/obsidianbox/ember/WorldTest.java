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

import com.flowpowered.math.vector.Vector3i;
import org.junit.Test;
import org.obsidianbox.ember.universe.voxel.Voxel;
import org.obsidianbox.ember.universe.voxel.material.MaterialManager;
import org.obsidianbox.ember.universe.voxel.material.MaterialRegistrationException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class WorldTest {
    private final MaterialManager materialManager = new MaterialManager();

    @Test
    public void testMaterialManager() {
        assertFalse(materialManager.get((String) null).isPresent());
        assertTrue(materialManager.get("ember:none").isPresent());
        try {
            materialManager.register(() -> "test:none");
        } catch (MaterialRegistrationException e) {
            fail("Material registration failed!");
        }

        boolean failed = false;
        try {
            materialManager.register(null);
        } catch (MaterialRegistrationException e) {
            failed = true;
        }
        if (!failed) {
            fail("Registering a null material should have failed!");
        }

        failed = false;
        try {
            materialManager.register(() -> null);
        } catch (MaterialRegistrationException e) {
            failed = true;
        }
        if (!failed) {
            fail("Registering a null material name should have failed!");
        }

        failed = false;
        try {
            materialManager.register(String::new);
        } catch (MaterialRegistrationException e) {
            failed = true;
        }
        if (!failed) {
            fail("Registering a material with an empty name should have failed!");
        }

        assertFalse(materialManager.NONE.getName().equals(materialManager.get("test:none").get().getName()));
    }
    @Test
    public void testVoxel() {
        final Voxel a = new Voxel(new Vector3i(0, 0, 0), materialManager.NONE);
        assertTrue(a.location.equals(new Vector3i(0, 0, 0)));
        assertTrue(a.getMaterial().get().equals(materialManager.NONE));
    }
}

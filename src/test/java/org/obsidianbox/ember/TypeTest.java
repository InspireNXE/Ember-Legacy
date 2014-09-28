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

import com.flowpowered.commons.BitSize;
import com.flowpowered.commons.StringToUniqueIntegerMap;
import org.junit.Test;
import org.obsidianbox.ember.util.atomic.AtomicPaletteStringStore;

public class TypeTest {

    private final StringToUniqueIntegerMap stringMap = new StringToUniqueIntegerMap("Material Map");

    @Test
    public void test() {
        stringMap.register("Test");
        stringMap.register("NotTest");

        final AtomicPaletteStringStore store = new AtomicPaletteStringStore(new BitSize(4), stringMap);
        assertTrue(!store.set(4096, 4096, 4096, "Test"));
        assertTrue(!store.set(-156, -156, -156, "NotTest"));
        assertFalse(store.set(5, 5, 5, "DaTest"));
        assertTrue(store.set(3, 3, 3, "Test"));
    }
}

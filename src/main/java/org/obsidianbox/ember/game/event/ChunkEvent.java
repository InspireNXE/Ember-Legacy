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
package org.obsidianbox.ember.game.event;

import org.obsidianbox.ember.game.Ember;
import org.obsidianbox.ember.game.universe.level.ChunkImpl;
import org.obsidianbox.ember.game.universe.level.WorldImpl;

public abstract class ChunkEvent extends WorldEvent {

    public final ChunkImpl chunk;

    public ChunkEvent(Ember game, WorldImpl world, ChunkImpl chunk) {
        super(game, world);
        this.chunk = chunk;
    }

    public static class Load extends ChunkEvent {

        public Load(Ember game, WorldImpl world, ChunkImpl chunk) {
            super(game, world, chunk);
        }
    }

    public static class Unload extends ChunkEvent {

        public Unload(Ember game, WorldImpl world, ChunkImpl chunk) {
            super(game, world, chunk);
        }
    }
}

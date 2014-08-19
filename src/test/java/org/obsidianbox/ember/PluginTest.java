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

import com.flowpowered.plugins.ContextCreator;
import com.flowpowered.plugins.PluginLoader;
import com.flowpowered.plugins.annotated.Disable;
import com.flowpowered.plugins.annotated.Enable;
import com.flowpowered.plugins.annotated.Plugin;
import com.flowpowered.plugins.simple.SimplePluginLoader;
import org.junit.Test;
import org.obsidianbox.ember.plugins.GameContext;
import org.obsidianbox.ember.plugins.GamePluginManager;

import static org.junit.Assert.fail;

public class PluginTest {
    @Test
    public void test()  {
        final Game game = new Game();
        final TestGamePluginManager manager = new TestGamePluginManager(game);

        manager.addLoader(new SimplePluginLoader<>(manager.getCreator(), getClass().getClassLoader()));
        //TODO Figure out how to test this by providing this class as a Path?
        //manager.addLoader(new AnnotatedPluginLoader<>(manager.getCreator(), getClass().getClassLoader()));
        com.flowpowered.plugins.Plugin<GameContext> plugin = manager.getPlugin("plugin");
        if (plugin != null) {
            fail("This should be null...");
        }
    }

    @Plugin(name = "plugin_test")
    public static class AnnotatedPluginTest {
        @Enable
        public void enable(GameContext context) {

        }

        @Disable
        public void disable(GameContext context) {

        }
    }

    public static class TraditionalPluginTest extends com.flowpowered.plugins.Plugin<GameContext> {
        @Override
        protected void onEnable() throws Exception {

        }

        @Override
        protected void onDisable() throws Exception {

        }
    }

    public static class TestGamePluginManager extends GamePluginManager {
        public TestGamePluginManager(Game game) {
            super(game);
        }

        @Override
        public ContextCreator<GameContext> getCreator() {
            return super.getCreator();
        }

        @Override
        public void addLoader(PluginLoader<GameContext> loader) {
            super.addLoader(loader);
        }
    }
}

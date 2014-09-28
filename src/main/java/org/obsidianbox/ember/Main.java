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

import static java.util.Arrays.asList;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception {
        FileSystem.init();
        final Configuration configuration = new Configuration(FileSystem.CONFIG_SETTINGS_PATH);
        configuration.load();
        parseArgs(args, configuration);
        final Game game = new Game(configuration);
        game.open();
    }

    public static void parseArgs(String[] args, Configuration configuration) throws Exception {
        final OptionParser parser = new OptionParser() {
            {
                acceptsAll(asList("l", "listen"))
                        .withOptionalArg()
                        .ofType(String.class);
                acceptsAll(asList("p", "port"))
                        .withOptionalArg()
                        .ofType(Integer.class);
                acceptsAll(asList("debug"))
                        .withOptionalArg();
            }
        };

        final OptionSet options = parser.parse(args);
        if (options.has("debug")) {
            configuration.setDebug(true);
        }
    }
}

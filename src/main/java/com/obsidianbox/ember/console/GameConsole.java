/**
 * This file is part of Ember, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014-2014 ObsidianBox <http://obsidianbox.org/>
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
package com.obsidianbox.ember.console;

import com.flowpowered.commons.console.CommandCallback;
import com.flowpowered.commons.console.Log4j2JLineConsole;
import com.obsidianbox.ember.Game;
import jline.console.completer.Completer;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GameConsole extends Log4j2JLineConsole {
    private final Game game;

    public GameConsole(Game game) {
        super(new GameCommandCallback(game), new GameCommandCompleter(), LoggerFactory.getLogger("Ember"), System.out, System.in);
        this.game = game;
    }
}

final class GameCommandCallback implements CommandCallback {
    private final Game game;

    public GameCommandCallback(Game game) {
        this.game = game;
    }

    @Override
    public void handleCommand(String command) {
        game.getRawCommandQueue().offer(command);
    }
}

final class GameCommandCompleter implements Completer {
    @Override
    public int complete(String buffer, int cursor, List<CharSequence> candidates) {
        return 0;
    }
}
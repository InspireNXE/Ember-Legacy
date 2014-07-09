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
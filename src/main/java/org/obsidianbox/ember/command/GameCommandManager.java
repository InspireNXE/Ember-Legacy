package org.obsidianbox.ember.command;

import com.flowpowered.commands.Command;
import com.flowpowered.commands.CommandException;
import com.flowpowered.commands.CommandManager;
import com.flowpowered.commands.CommandProvider;
import com.flowpowered.commands.annotated.AnnotatedCommandExecutorFactory;
import org.obsidianbox.ember.Game;
import org.slf4j.LoggerFactory;

public class GameCommandManager extends CommandManager {
    private final Game game;
    private AnnotatedCommandExecutorFactory factory;

    public GameCommandManager(Game game) {
        this.game = game;

        final CommandProvider provider = new CommandProvider() {
            @Override
            public String getName() {
                return "game";
            }
        };
        setRootCommand(getCommand(provider, "root"));
        factory = new AnnotatedCommandExecutorFactory(this, provider, LoggerFactory.getLogger("Ember"));
    }

    public void onTick(long dt) {
        String rawCommand;
        while ((rawCommand = game.getConsole().getCallbackQueue().poll()) != null) {
            try {
                game.getConsole().processCommand(rawCommand);
            } catch (CommandException e) {
                game.logger.error("Exception caught processing command: " + rawCommand, e);
            }
        }
    }

    public void create(Class<?> commands) {
        factory.create(commands);
    }

    public void create(Class<?> commands, Command parent) {
        factory.create(commands, parent);
    }

    public void create(Object instance) {
        factory.create(instance);
    }

    public void create(Object instance, Command parent) {
        factory.create(instance, parent);
    }
}

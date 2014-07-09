package com.obsidianbox.ember.command;

import com.flowpowered.commands.CommandArguments;
import com.flowpowered.commands.CommandException;
import com.flowpowered.commands.CommandSender;
import com.flowpowered.commands.annotated.CommandDescription;
import com.flowpowered.commands.annotated.Permissible;
import com.obsidianbox.ember.Game;

public class Commands {
    private final Game game;

    public Commands(Game game) {
        this.game = game;
    }

    @CommandDescription (name = "stop", usage = "stop", desc = "Stops the game", help = "Use this command only when you want to stop the game")
    @Permissible("game.command.stop")
    private void onCommandStop(CommandSender sender, CommandArguments args) throws CommandException {
        game.close();
    }
}

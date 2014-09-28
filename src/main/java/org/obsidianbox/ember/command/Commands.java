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
package org.obsidianbox.ember.command;

import com.flowpowered.commands.CommandArguments;
import com.flowpowered.commands.CommandException;
import com.flowpowered.commands.CommandSender;
import com.flowpowered.commands.annotated.CommandDescription;
import com.flowpowered.commands.annotated.Permissible;
import org.obsidianbox.ember.Game;

import java.net.InetSocketAddress;

public class Commands {

    private final Game game;

    public Commands(Game game) {
        this.game = game;
    }

    @CommandDescription(name = "stop", usage = "stop", desc = "Stops the game", help = "Use this command only when you" +
                                                                                       " want to stop the game")
    @Permissible("game.command.stop")
    private void onCommandStop(CommandSender sender, CommandArguments args) throws CommandException {
        game.close();
    }

    @CommandDescription(name = "listen", usage = "listen [address] <port>", desc = "Listens for connections",
                        help = "Use this command only when you want the game to listen for connections. Submitting this command" +
                               " with active connections will cause those connections to cease.")
    @Permissible("game.command.listen")
    private void onCommandListen(CommandSender sender, CommandArguments args) throws CommandException {
        game.network.bind(new InetSocketAddress(args.popInteger("address", 25500)));
    }

    @CommandDescription(name = "connect", usage = "connect [address] <port>", desc = "Connects to an address",
                        help = "Use this command only when you want to connect to an address. This will end any connections you" +
                               " have currently open")
    @Permissible("game.command.connect")
    private void onCommandConnect(CommandSender sender, CommandArguments args) throws CommandException {
        game.network.connect(new InetSocketAddress(args.popInteger("address", 25500)));
    }

    @CommandDescription(name = "disconnect", usage = "disconnect", desc = "Disconnects from an address",
                        help = "Use this command only when you want to disconnect.")
    @Permissible("game.command.connect")
    private void onCommandDisconnect(CommandSender sender, CommandArguments args) throws CommandException {
        game.network.disconnect();
    }

    @CommandDescription(name = "version", usage = "version", desc = "Displays the version of Ember in use",
                        help = "Use this command to display the version of Ember in use.")
    @Permissible("game.command.version")
    private void onCommandVersion(CommandSender sender, CommandArguments args) throws CommandException {
        sender.sendMessage("Running Ember version: " + (Game.VERSION.isPresent() ? Game.VERSION.get() : "UNKNOWN"));
    }
}

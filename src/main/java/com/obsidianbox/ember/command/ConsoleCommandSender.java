package com.obsidianbox.ember.command;

import java.util.Collections;
import java.util.Set;

import com.flowpowered.chat.ChatReceiver;
import com.flowpowered.commands.CommandArguments;
import com.flowpowered.commands.CommandException;
import com.flowpowered.commands.CommandManager;
import com.flowpowered.commands.CommandSender;
import com.flowpowered.permissions.PermissionDomain;

public class ConsoleCommandSender implements CommandSender {
    private final CommandManager manager;

    public ConsoleCommandSender(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void processCommand(String commandLine) throws CommandException {
        this.manager.getRootCommand().execute(this, new CommandArguments(commandLine.split(" ")));
    }

    @Override
    public void sendMessage(String message) {
        sendMessageRaw(message, "");
    }

    @Override
    public void sendMessage(ChatReceiver from, String message) {
        sendMessage(message);
    }

    @Override
    public void sendMessageRaw(String message, String type) {
    }

    @Override
    public String getName() {
        return "console";
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }

    @Override
    public boolean hasPermission(String permission, PermissionDomain domain) {
        return true;
    }

    @Override
    public boolean isInGroup(String group) {
        return false;
    }

    @Override
    public boolean isInGroup(String group, PermissionDomain domain) {
        return false;
    }

    @Override
    public Set<String> getGroups() {
        return Collections.emptySet();
    }

    @Override
    public Set<String> getGroups(PermissionDomain domain) {
        return Collections.emptySet();
    }
}

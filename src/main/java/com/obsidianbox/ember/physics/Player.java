package com.obsidianbox.ember.physics;

import com.flowpowered.chat.ChatReceiver;
import com.flowpowered.commands.CommandArguments;
import com.flowpowered.commands.CommandException;
import com.flowpowered.commands.CommandSender;
import com.flowpowered.networking.session.Session;
import com.flowpowered.permissions.PermissionDomain;
import com.obsidianbox.ember.Game;
import com.obsidianbox.ember.event.GameEvent;
import com.obsidianbox.ember.physics.util.Transform;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

public class Player extends Entity implements CommandSender {
    private final String name;
    private final Session session;

    public Player(Game game, int id, Transform transform, String name, Session session) {
        super(game, id, transform);
        this.name = name;
        this.session = session;
    }

    protected Player(Game game, UUID uuid, int id, String name, Transform transform, Session session) {
        super(game, uuid, id, transform);
        this.name = name;
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    @Override
    public void processCommand(String commandLine) throws CommandException {
        game.getCommandManager().executeCommand(this, new CommandArguments(commandLine.split(" ")));
    }

    @Override
    public void sendMessage(String message) {
        game.getEventManager().callEvent(new GameEvent.Chat(game, this, message));
    }

    @Override
    public void sendMessage(ChatReceiver from, String message) {
        game.getEventManager().callEvent(new GameEvent.Chat(game, this, from, message));
    }

    @Override
    public void sendMessageRaw(String message, String type) {
        game.getEventManager().callEvent(new GameEvent.Chat(game, this, message));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasPermission(String permission) {
        return !game.getEventManager().callEvent(new GameEvent.Permission(game, this, permission)).isCancelled();
    }

    @Override
    public boolean hasPermission(String permission, PermissionDomain domain) {
        return !game.getEventManager().callEvent(new GameEvent.Permission(game, this, domain, permission)).isCancelled();
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

    @Override
    public String toString() {
        return "Player{" +
                "name=" + name +
                ", uuid=" + uuid +
                ", id=" + id +
                ", transform=" + transform +
                ", isSavable=" + isSavable +
                '}';
    }
}

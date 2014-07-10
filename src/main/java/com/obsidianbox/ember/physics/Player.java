package com.obsidianbox.ember.physics;

import com.flowpowered.chat.ChatReceiver;
import com.flowpowered.commands.CommandException;
import com.flowpowered.commands.CommandSender;
import com.flowpowered.networking.session.Session;
import com.flowpowered.permissions.PermissionDomain;
import com.obsidianbox.ember.Game;
import com.obsidianbox.ember.event.PlayerEvent;
import com.obsidianbox.ember.physics.util.Transform;

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

    @Override
    public void processCommand(String commandLine) throws CommandException {

    }

    @Override
    public void sendMessage(String message) {

    }

    @Override
    public void sendMessage(ChatReceiver from, String message) {

    }

    @Override
    public void sendMessageRaw(String message, String type) {

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasPermission(String permission) {
        return !game.getEventManager().callEvent(new PlayerEvent.PermissionEvent(game, this, permission)).isCancelled();
    }

    @Override
    public boolean hasPermission(String permission, PermissionDomain domain) {
        return !game.getEventManager().callEvent(new PlayerEvent.PermissionEvent(game, this, domain, permission)).isCancelled();
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
        return null;
    }

    @Override
    public Set<String> getGroups(PermissionDomain domain) {
        return null;
    }
}

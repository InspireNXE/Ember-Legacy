package org.obsidianbox.ember.universe;

import com.flowpowered.commons.ticking.TickingElement;
import com.flowpowered.plugins.Plugin;
import org.obsidianbox.ember.Game;
import org.obsidianbox.ember.event.WorldEvent;
import org.obsidianbox.ember.universe.generator.WorldGenerator;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Universe extends TickingElement {
    public final Game game;
    private final ConcurrentHashMap<UUID, World> worlds = new ConcurrentHashMap<>();

    public Universe(Game game) {
        super("ember - universe", 20);
        this.game = game;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onTick(long dt) {

    }

    @Override
    public void onStop() {
        worlds.forEachValue(worlds.size(), TickingElement::stop);
    }

    public World loadWorld(Plugin plugin, String identifier, WorldGenerator generator) {
        for (World w : worlds.values()) {
            if (w.identifier.equalsIgnoreCase(identifier)) {
                return w;
            }
        }
        // TODO: Load world from file if present
        final World world = new World(game, plugin, identifier, generator);
        // TODO: Fire appropriate event based on if the world was just created or not
        game.getEventManager().callEvent(new WorldEvent.Create(game, world));
        world.start();
        game.getEventManager().callEvent(new WorldEvent.Load(game, world));
        worlds.put(world.uuid, world);
        return world;
    }
}

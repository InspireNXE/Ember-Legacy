package com.obsidianbox.ember.plugin.example;

import com.flowpowered.events.EventHandler;
import com.flowpowered.events.Order;
import com.obsidianbox.ember.event.GameEvent;
import com.obsidianbox.ember.event.PluginEvent;
import com.obsidianbox.ember.plugin.Plugin;

@Plugin(id = "ExamplePlugin", name = "Example", version = "1.0.0-SNAPSHOT")
public class Example {
    @EventHandler
    public void onPluginLoad(PluginEvent.PluginLoadEvent event) {
        event.game.logger.info("I've been loaded :)!");
    }

    @EventHandler
    public void onPluginEnable(PluginEvent.PluginEnableEvent event) {
        event.game.logger.info("I've been enabled :D!");
    }

    @EventHandler
    public void onPluginDisable(PluginEvent.PluginDisableEvent event) {
        event.game.logger.info("I've been disabled D:!");
    }

    @EventHandler
    public void onPluginReload(PluginEvent.PluginReloadEvent event) {
        event.game.logger.info("I've been reloaded >:D!");
    }

    @EventHandler()
    public void onGameStart(GameEvent.GameStartEvent event) {
        event.game.logger.info("GameStartEvent fired!");
    }
}

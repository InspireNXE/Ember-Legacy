package org.obsidianbox.ember.resource;

import org.obsidianbox.ember.Game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSystem {
    public static final Path LOGS_PATH = Paths.get("logs");
    public static final Path PLUGINS_PATH = Paths.get("plugins");
    private final Game game;

    public FileSystem(Game game) {
        this.game = game;
    }

    public void init() throws IOException {
        if (!Files.exists(PLUGINS_PATH)) {
            game.logger.warn("Plugins directory was not found. Ignore this is this is your first time running Ember. Otherwise, this is a problem.");
            Files.createDirectory(PLUGINS_PATH);
        }
    }
}

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
package org.obsidianbox.ember;

import java.io.IOException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FileSystem {

    public static final Path LOGS_PATH = Paths.get("logs");
    public static final Path PLUGINS_PATH = Paths.get("plugins");
    public static final Path WORLDS_PATH = Paths.get("worlds");
    public static final Path CONFIG_PATH = Paths.get("config");
    public static final Path CONFIG_SETTINGS_PATH = Paths.get(CONFIG_PATH.toString(), "settings.yml");

    protected FileSystem() {}

    /**
     * Stolen from flow-engine, credits to Waterpicker (cause I'm lazy) edits by me.
     * @param path Where to get URLs from
     * @param blob File extension to look for
     * @return Collection of urls found
     */
    public static Collection<URL> getURLs(Path path, String blob) {
        final List<URL> result = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, blob)) {
            for (Path entry : stream) {
                result.add(entry.toUri().toURL());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return Collections.unmodifiableCollection(result);
    }

    /**
     * Deploys all game directories and settings.
     * @throws IOException If any directory creation fails
     */
    public static void deploy() throws IOException {
        if (Files.notExists(CONFIG_PATH)) {
            Game.LOGGER.warn("Config directory was not found. Ignore this is this is your first time running Ember. Otherwise, this may be a problem.");
            Files.createDirectory(CONFIG_PATH);
        }
        if (Files.notExists(CONFIG_SETTINGS_PATH)) {
            Files.copy(FileSystem.class.getResourceAsStream("/config/settings.yml"), CONFIG_SETTINGS_PATH);
        }
        if (Files.notExists(PLUGINS_PATH)) {
            Game.LOGGER.warn("Plugins directory was not found. Ignore this is this is your first time running Ember. Otherwise, this may be a problem.");
            Files.createDirectory(PLUGINS_PATH);
        }
        if (Files.notExists(WORLDS_PATH)) {
            Game.LOGGER.warn("Worlds directory was not found. Ignore this is this is your first time running Ember. Otherwise, this may be a problem.");
            Files.createDirectory(WORLDS_PATH);
        }
    }
}

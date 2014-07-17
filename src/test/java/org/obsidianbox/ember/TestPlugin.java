package org.obsidianbox.ember;

import org.obsidianbox.ember.plugin.Plugin;
import org.obsidianbox.ember.plugin.Plugin.Instance;

@Plugin(id = "TestPlugin", name = "Test")
public class TestPlugin {
    @Instance(id = "TestPlugin")
    public static TestPlugin INSTANCE;
}

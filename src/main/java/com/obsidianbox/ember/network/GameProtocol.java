package com.obsidianbox.ember.network;

import com.flowpowered.networking.protocol.AbstractProtocol;
import org.slf4j.LoggerFactory;

public abstract class GameProtocol extends AbstractProtocol {
    public GameProtocol(String name) {
        super(name, LoggerFactory.getLogger("Ember"));
    }
}

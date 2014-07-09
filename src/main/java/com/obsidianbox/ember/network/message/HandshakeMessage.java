package com.obsidianbox.ember.network.message;

import com.flowpowered.networking.Message;

public class HandshakeMessage implements Message {
    public final byte version;
    public final HandshakeState state;

    public HandshakeMessage(byte version, HandshakeState state) {
        this.version = version;
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HandshakeMessage that = (HandshakeMessage) o;

        return version == that.version && state == that.state;

    }

    @Override
    public int hashCode() {
        int result = (int) version;
        result = 31 * result + state.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "HandshakeMessage{" +
                "version=" + version +
                ", state=" + state +
                '}';
    }
}

enum HandshakeState {
    LOGIN((byte) 0),
    STATUS((byte) 1);

    private final byte flag;

    HandshakeState(byte flag) {
        this.flag = flag;
    }

    public byte value() {
        return flag;
    }
}
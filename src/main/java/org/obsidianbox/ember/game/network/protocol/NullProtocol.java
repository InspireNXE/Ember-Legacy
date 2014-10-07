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
package org.obsidianbox.ember.game.network.protocol;

import com.flowpowered.networking.Codec;
import com.flowpowered.networking.Message;
import com.flowpowered.networking.MessageHandler;
import com.flowpowered.networking.exception.UnknownPacketException;
import io.netty.buffer.ByteBuf;
import org.obsidianbox.ember.Ember;
import org.obsidianbox.ember.game.network.GameProtocol;

import java.io.IOException;

public class NullProtocol extends GameProtocol {
    public NullProtocol(Ember game) {
        super(game, "Null");
    }

    @Override
    public <M extends Message> MessageHandler<?, M> getMessageHandle(Class<M> message) {
        return null;
    }

    @Override
    public Codec<?> readHeader(ByteBuf buf) throws UnknownPacketException {
        return new NullCodec();
    }

    @Override
    public <M extends Message> Codec.CodecRegistration getCodecRegistration(Class<M> message) {
        return new Codec.CodecRegistration(0, new NullCodec());
    }

    @Override
    public ByteBuf writeHeader(ByteBuf header, Codec.CodecRegistration codec, ByteBuf data) {
        return header;
    }

    private static class NullCodec implements Codec<NullMessage> {

        @Override
        public NullMessage decode(ByteBuf buffer) throws IOException {
            return new NullMessage();
        }

        @Override
        public ByteBuf encode(ByteBuf buf, NullMessage message) throws IOException {
            return buf;
        }
    }

    private static class NullMessage implements Message {

    }
}

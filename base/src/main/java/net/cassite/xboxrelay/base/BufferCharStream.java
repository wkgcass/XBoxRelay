package net.cassite.xboxrelay.base;

import io.vertx.core.buffer.Buffer;
import vjson.CharStream;

public class BufferCharStream implements CharStream {
    private final Buffer buffer;
    private int off;

    public BufferCharStream(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public boolean hasNext(int i) {
        return buffer.length() >= off + i;
    }

    @Override
    public char moveNextAndGet() {
        return (char) buffer.getByte(off++);
    }

    @Override
    public char peekNext(int i) {
        return (char) buffer.getByte(off + i - 1);
    }
}

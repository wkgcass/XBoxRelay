package net.cassite.xboxrelay.base;

import io.vertx.core.Context;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;
import io.vertx.core.net.SocketAddress;
import io.vproxy.base.util.LogType;
import io.vproxy.base.util.Logger;
import vjson.JSON;

public abstract class BaseNetSocketHandler {
    private final Context ctx;
    private final NetSocket sock;
    private int state = 0;
    // 0: expecting length[0]
    // 1: expecting length[1]
    // 2: expecting length[2]
    // 3: expecting data

    private byte lenA;
    private byte lenB;
    private Buffer data;
    private int remainingDataLen;

    public BaseNetSocketHandler(Context ctx, NetSocket sock) {
        this.ctx = ctx;
        this.sock = sock;
        sock.handler(this::handle);
    }

    private void handle(Buffer buffer) {
        assert Logger.lowLevelDebug("received buffer from " + remoteAddress() + ": " + buffer);
        int offset = 0;
        loop:
        while (true) {
            switch (state) {
                case 0:
                    if (buffer.length() > offset) {
                        lenA = buffer.getByte(offset);
                        offset += 1;
                        state = 1;
                    } else {
                        break loop;
                    }
                case 1:
                    if (buffer.length() > offset) {
                        lenB = buffer.getByte(offset);
                        offset += 1;
                        state = 2;
                    } else {
                        break loop;
                    }
                case 2:
                    if (buffer.length() > offset) {
                        var lenC = buffer.getByte(offset);
                        offset += 1;
                        state = 3;
                        remainingDataLen = ((lenA & 0xff) << 16) | ((lenB & 0xff) << 8) | (lenC & 0xff);
                    } else {
                        break loop;
                    }
                case 3:
                    if (buffer.length() >= offset + remainingDataLen) {
                        var sub = buffer.slice(offset, offset + remainingDataLen);
                        if (data == null) {
                            data = sub;
                        } else {
                            data = data.appendBuffer(sub);
                        }
                        offset += remainingDataLen;
                        state = 0;
                        handleData();
                    } else if (buffer.length() > offset) {
                        var sub = buffer.slice(offset, buffer.length());
                        if (data == null) {
                            data = sub.copy();
                        } else {
                            data = data.appendBuffer(sub);
                        }
                        var readBytes = buffer.length() - offset;
                        remainingDataLen -= readBytes;
                        offset += readBytes;
                    } else {
                        break loop;
                    }
            }
        }
    }

    private void handleData() {
        Message msg;
        try {
            msg = JSON.deserialize(new BufferCharStream(data), Message.messageTypeRule());
        } catch (Exception e) {
            Logger.error(LogType.INVALID_EXTERNAL_DATA,"failed deserializing data " + data + " from " + sock.remoteAddress(), e);
            close();
            return;
        } finally {
            data = null;
        }
        if (msg instanceof HeartBeatMessage hb) {
            assert Logger.lowLevelDebug("received heatbeat from " + sock.remoteAddress() + " " + msg);
            if (hb.type == HeartBeatMessage.TYPE_PING) {
                send(new HeartBeatMessage(HeartBeatMessage.TYPE_PONG));
            }
            return;
        }
        handle(msg);
    }

    public void _send(Buffer msg) {
        ctx.runOnContext(v -> sock.write(msg));
    }

    public void send(Message msg) {
        _send(msg.toBuffer());
    }

    protected abstract void handle(Message msg);

    protected SocketAddress remoteAddress() {
        return sock.remoteAddress();
    }

    public void close() {
        sock.close();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + remoteAddress();
    }
}

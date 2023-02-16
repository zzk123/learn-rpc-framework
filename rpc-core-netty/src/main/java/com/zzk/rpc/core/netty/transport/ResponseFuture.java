package com.zzk.rpc.core.netty.transport;

import com.zzk.rpc.core.netty.transport.command.Command;
import java.util.concurrent.CompletableFuture;

public class ResponseFuture {

    private final int requestId;
    private final CompletableFuture<Command> future;
    private final long timestamp;

    public ResponseFuture(int requestId, CompletableFuture<Command> future) {
        this.requestId = requestId;
        this.future = future;
        timestamp = System.nanoTime();
    }

    public int getRequestId() {
        return requestId;
    }

    public CompletableFuture<Command> getFuture() {
        return future;
    }

    long getTimestamp() {
        return timestamp;
    }
}

package com.zzk.rpc.core.netty.transport;

import com.zzk.rpc.core.netty.transport.command.Command;

import java.util.concurrent.CompletableFuture;

public interface Transport {

    /**
     * 发送请求命令
     * @param request
     * @return
     */
    CompletableFuture<Command> send(Command request);
}

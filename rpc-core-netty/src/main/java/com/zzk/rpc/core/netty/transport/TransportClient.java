package com.zzk.rpc.core.netty.transport;

import java.io.Closeable;
import java.net.SocketAddress;
import java.util.concurrent.TimeoutException;

/**
 * 调用方接口定义
 */
public interface TransportClient extends Closeable {

    /**
     * 创建请求命令
     * @param address
     * @param connectionTimeout
     * @return
     * @throws InterruptedException
     * @throws TimeoutException
     */
    Transport createTransport(SocketAddress address, long connectionTimeout) throws InterruptedException, TimeoutException;

    @Override
    void close();
}

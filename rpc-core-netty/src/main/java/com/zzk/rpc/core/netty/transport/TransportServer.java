package com.zzk.rpc.core.netty.transport;

/**
 * 服务提供方调用接口
 */
public interface TransportServer {

    /**
     * 启动，注册服务端口
     * @param requestHandlerRegistry
     * @param port
     * @throws Exception
     */
    void start(RequestHandlerRegistry requestHandlerRegistry, int port) throws Exception;

    /**
     * 停止
     */
    void stop();
}

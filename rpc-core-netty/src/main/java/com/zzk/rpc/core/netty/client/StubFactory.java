package com.zzk.rpc.core.netty.client;


import com.zzk.rpc.core.netty.transport.Transport;

/**
 * 工厂类接口实现
 */
public interface StubFactory {

    /**
     * 创建Stub
     * @param transport
     * @param serviceClass
     * @param <T>
     * @return
     */
    <T> T createStub(Transport transport, Class<T> serviceClass);
}

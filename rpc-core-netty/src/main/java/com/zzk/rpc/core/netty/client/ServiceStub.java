package com.zzk.rpc.core.netty.client;

import com.zzk.rpc.core.netty.transport.Transport;

public interface ServiceStub {

    /**
     * 配置参数
     * @param transport
     */
    void setTransport(Transport transport);

}

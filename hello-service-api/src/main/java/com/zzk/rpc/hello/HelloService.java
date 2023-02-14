package com.zzk.rpc.hello;

/**
 * 简单 demo 接口
 */
public interface HelloService {

    /**
     * 调用业务接口
     * @param name
     * @return
     */
    public String hello(String name);
}

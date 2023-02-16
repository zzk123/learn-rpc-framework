package com.zzk.rpc.core.netty.server;

/**
 * 服务提供注册
 */
public interface ServiceProviderRegistry {

    <T> void addServiceProvider(Class<? extends T> serviceClass, T serviceProvider);
}

package com.zzk.rpc.api;

import com.zzk.rpc.spi.ServiceSupport;

import java.io.Closeable;
import java.net.URI;
import java.util.Collection;

/**
 * 远程调用接口定义
 */
public interface RpcAccessPoint extends Closeable {

    /**
     * 客户端获取远程服务的引用
     * @param uri
     * @param serivceClass
     * @param <T>
     * @return
     */
    <T> T getRemoteService(URI uri, Class<T> serivceClass);

    /**
     * 服务端注册服务的实现实例
     * @param service
     * @param serviceClass
     * @param <T>
     * @return
     */
    <T> URI addServiceProvider(T service, Class<T> serviceClass);


    /**
     * 服务端启动RPC框架，监听接口，开始提供远程服务
     * @return
     * @throws Exception
     */
    Closeable startServer() throws Exception;

    /**
     * 获取注册中心的引用
     * @param nameServiceUri
     * @return
     */
    default NameService getNameService(URI nameServiceUri){
        //加载对应的注册中心实现类
        Collection<NameService> nameServices = ServiceSupport.loadAll(NameService.class);
        for(NameService nameService : nameServices){
            //根据协议匹配
            if(nameService.supportedSchemes().contains(nameServiceUri.getScheme())){
                nameService.connect(nameServiceUri);
                return nameService;
            }
        }
        return null;
    }
}

package com.zzk.rpc.api;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;

/**
 * 定义注册中心方法
 */
public interface NameService {

    /**
     * 所有支持的协议
     * @return
     */
    Collection<String> supportedSchemes();

    /**
     * 连接注册中心
      * @param nameServiceUri
     */
    void connect(URI nameServiceUri);


    /**
     * 注册服务
     * @param serviceName
     * @param uri
     * @throws IOException
     */
    void registerService(String serviceName, URI uri) throws IOException;


    /**
     * 查询服务地址
     * @param serviceName
     * @return
     * @throws IOException
     */
    URI lookupService(String serviceName) throws IOException;
}

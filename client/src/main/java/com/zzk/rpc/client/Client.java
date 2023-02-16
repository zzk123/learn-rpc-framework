package com.zzk.rpc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.zzk.rpc.api.NameService;
import com.zzk.rpc.api.RpcAccessPoint;
import com.zzk.rpc.hello.HelloService;
import com.zzk.rpc.spi.ServiceSupport;

import java.io.File;
import java.io.IOException;
import java.net.URI;


/**
 * @ClassName Client
 * @Description TODO
 * @Author zzk
 * @Date 2023/2/16 23:13
 **/
public class Client {

    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) throws IOException {
        String serviceName = HelloService.class.getCanonicalName();
        File tmpDirFile = new File(System.getProperty("java.io.tmpdir"));
        File file = new File(tmpDirFile, "simple_rpc_name_service.data");
        String name = "Master MQ";
        try(RpcAccessPoint rpcAccessPoint = ServiceSupport.load(RpcAccessPoint.class)){
            NameService nameService = rpcAccessPoint.getNameService(file.toURI());
            assert nameService != null;
            URI uri = nameService.lookupService(serviceName);
            assert uri != null;
            logger.info("找到服务{}，提供者: {}.", serviceName, uri);
            HelloService helloService = rpcAccessPoint.getRemoteService(uri, HelloService.class);
            logger.info("请求服务, name: {}...", name);
            String response = helloService.hello(name);
            logger.info("收到响应: {}.", response);
        }
    }
}

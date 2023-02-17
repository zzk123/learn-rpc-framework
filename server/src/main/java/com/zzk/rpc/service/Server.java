package com.zzk.rpc.service;

import com.zzk.rpc.api.NameService;
import com.zzk.rpc.api.RpcAccessPoint;
import com.zzk.rpc.hello.HelloService;
import com.zzk.rpc.spi.ServiceSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.File;
import java.net.URI;

/**
 * 服务启动
 * 服务提供
 */
public class Server {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws Exception {
        String serviceName = HelloService.class.getCanonicalName();
        File tmpDirFile = new File(System.getProperty("java.io.tmpdir"));
        File file = new File(tmpDirFile, "simple_rpc_name_service.data");
        HelloService helloService = new HelloServiceImpl();
        logger.info("创建并启动RpcAccessPoint...");
        try(RpcAccessPoint rpcAccessPoint = ServiceSupport.load(RpcAccessPoint.class);
            Closeable ignored = rpcAccessPoint.startServer()){
                NameService nameService = rpcAccessPoint.getNameService(file.toURI());
                assert nameService != null;
                logger.info("向RpcAccessPoint注册{}服务...", serviceName);
                URI uri = rpcAccessPoint.addServiceProvider(helloService, HelloService.class);
                logger.info("服务名: {}, 向NameService注册...", serviceName);
                nameService.registerService(serviceName, uri);
                logger.info("开始提供服务，按任何键退出.");
                //noinspection ResultOfMethodCallIgnored
                System.in.read();
                logger.info("Bye!");
        }
    }
}

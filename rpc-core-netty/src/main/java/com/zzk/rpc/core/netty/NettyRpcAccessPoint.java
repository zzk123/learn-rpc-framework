package com.zzk.rpc.core.netty;

import com.zzk.rpc.api.RpcAccessPoint;
import com.zzk.rpc.core.netty.client.StubFactory;
import com.zzk.rpc.core.netty.server.ServiceProviderRegistry;
import com.zzk.rpc.core.netty.transport.RequestHandlerRegistry;
import com.zzk.rpc.core.netty.transport.Transport;
import com.zzk.rpc.core.netty.transport.TransportClient;
import com.zzk.rpc.core.netty.transport.TransportServer;
import com.zzk.rpc.spi.ServiceSupport;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

/**
 * netty 远程调用实现
 */
public class NettyRpcAccessPoint implements RpcAccessPoint {

    private final String host = "localhost";
    private final int port = 9999;
    private final URI uri = URI.create("rpc://" + host + ":" + port);
    private TransportServer server = null;
    private TransportClient client = ServiceSupport.load(TransportClient.class);
    private final Map<URI, Transport> clientMap = new ConcurrentHashMap<>();
    private final StubFactory stubFactory = ServiceSupport.load(StubFactory.class);
    private final ServiceProviderRegistry serviceProviderRegistry = ServiceSupport.load(ServiceProviderRegistry.class);

    /**
     * 客户端 ===========================================
     */
    @Override
    public <T> T getRemoteService(URI uri, Class<T> serivceClass) {
        Transport transport = clientMap.computeIfAbsent(uri, this::createTransport);
        return stubFactory.createStub(transport, serivceClass);
    }

    private Transport createTransport(URI uri){
        try{
            return client.createTransport(new InetSocketAddress(uri.getHost(), uri.getPort()), 30000L);
        }catch (InterruptedException | TimeoutException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 服务端 =============================================
     */

    @Override
    public synchronized <T> URI addServiceProvider(T service, Class<T> serviceClass) {
        serviceProviderRegistry.addServiceProvider(serviceClass, service);
        return uri;
    }

    @Override
    public synchronized Closeable startServer() throws Exception {
        if(null == server){
            server = ServiceSupport.load(TransportServer.class);
            server.start(RequestHandlerRegistry.getInstance(), port);
        }
        return () ->{
            if(null != server){
                server.stop();
            }
        };
    }

    @Override
    public void close() throws IOException {
        if(null != server){
            server.stop();
        }
        client.close();
    }
}

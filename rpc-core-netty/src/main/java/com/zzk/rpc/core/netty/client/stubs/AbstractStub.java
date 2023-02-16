package com.zzk.rpc.core.netty.client.stubs;

import com.zzk.rpc.core.netty.client.RequestIdSupport;
import com.zzk.rpc.core.netty.client.ServiceStub;
import com.zzk.rpc.core.netty.client.ServiceTypes;
import com.zzk.rpc.core.netty.serialize.SerializeSupport;
import com.zzk.rpc.core.netty.transport.Transport;
import com.zzk.rpc.core.netty.transport.command.Code;
import com.zzk.rpc.core.netty.transport.command.Command;
import com.zzk.rpc.core.netty.transport.command.Header;
import com.zzk.rpc.core.netty.transport.command.ResponseHeader;

import java.util.concurrent.ExecutionException;

/**
 * 抽象类实现
 */
public class AbstractStub implements ServiceStub {

    protected Transport transport;

    /**
     * 调用远程方法
     * @param request
     * @return
     */
    protected byte[] invokeRemote(RpcRequest request){
        Header header = new Header(ServiceTypes.TYPE_RPC_REQUEST, 1, RequestIdSupport.next());
        byte [] payload = SerializeSupport.serialize(request);
        Command requestCommand = new Command(header, payload);
        try{
            Command responseCommand = transport.send(requestCommand).get();
            ResponseHeader responseHeader = (ResponseHeader) responseCommand.getHeader();
            if(responseHeader.getCode() == Code.SUCCESS.getCode()) {
                return responseCommand.getPayload();
            } else {
                throw new Exception(responseHeader.getError());
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e.getCause());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setTransport(Transport transport) {
        this.transport = transport;
    }
}

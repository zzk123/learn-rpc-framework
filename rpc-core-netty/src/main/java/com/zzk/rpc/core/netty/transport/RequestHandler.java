package com.zzk.rpc.core.netty.transport;

import com.zzk.rpc.core.netty.transport.command.Command;

/**
 * 请求处理器
 */
public interface RequestHandler {


    /**
     * 处理请求
     * @param requestCommand
     * @return
     */
    Command handle(Command requestCommand);

    /**
     * 支持请求命令
     * @return
     */
    int type();
}

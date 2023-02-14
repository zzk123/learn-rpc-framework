package com.zzk.rpc;

import java.util.Collection;

public interface NameService {

    /**
     * 所有支持的协议
     * @return
     */
    Collection<String> supportedSchemes();
}

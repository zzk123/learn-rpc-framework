package com.zzk.rpc.core.netty.client;

import com.itranswarp.compiler.JavaStringCompiler;
import com.zzk.rpc.core.netty.transport.Transport;

import java.util.Map;

/**
 * 动态代理实现工厂类
 */
public class DynamicStubFactory implements StubFactory {
    //定义模板
    private final static String STUB_SOURCE_TEMPLATE =
            "package com.zzk.rpc.core.netty.client.stubs;\n" +
            "import com.zzk.rpc.core.netty.serialize.SerializeSupport;\n" +
            "\n" +
            "public class %s extends AbstractStub implements %s {\n" +
            "    @Override\n" +
            "    public String %s(String arg) {\n" +
            "        return SerializeSupport.parse(\n" +
            "                invokeRemote(\n" +
            "                        new RpcRequest(\n" +
            "                                \"%s\",\n" +
            "                                \"%s\",\n" +
            "                                SerializeSupport.serialize(arg)\n" +
            "                        )\n" +
            "                )\n" +
            "        );\n" +
            "    }\n" +
            "}";

    /**
     * 创建对应的代理类
     * @param transport
     * @param serviceClass
     * @param <T>
     * @return
     */
    @Override
    public <T> T createStub(Transport transport, Class<T> serviceClass) {
        try{
            String stubSimpleName = serviceClass.getSimpleName() + "Stub";
            String classFullName = serviceClass.getName();
            String stubFullName = "com.zzk.rpc.core.netty.client.stubs." + stubSimpleName;
            String methodName = serviceClass.getMethods()[0].getName();

            String source = String.format(STUB_SOURCE_TEMPLATE, stubSimpleName, classFullName,
                    methodName, classFullName, methodName);
            //编译源代码
            JavaStringCompiler compiler = new JavaStringCompiler();
            Map<String, byte[]> results = compiler.compile(stubSimpleName + ".java", source);
            //加载编译好的类
            Class<?> clazz = compiler.loadClass(stubFullName, results);

            //把Transport赋值给桩
            ServiceStub stubInstance = (ServiceStub) clazz.newInstance();
            stubInstance.setTransport(transport);

            // 返回这个桩
            return (T) stubInstance;
        }catch (Throwable t){
            throw new RuntimeException(t);
        }
    }
}

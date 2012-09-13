package org.n3r.cxf.utils;

import java.lang.reflect.Method;

import javax.xml.ws.Service;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.n3r.core.joor.Reflect;

import static org.apache.commons.lang3.StringUtils.*;
import static org.n3r.core.lang.RMethod.*;
import static org.n3r.cxf.utils.WebServiceCallUtils.*;
import static org.n3r.cxf.utils.WebServiceRoutingUtils.*;

public class WebServiceProxyFactory {

    public static Object createWebServiceProxy(String serviceName, String module) {
        return module == null ? createProxy(serviceName) : createProxy(serviceName, module);
    }

    private static Object createProxy(String serviceName) {
        Class<?> proxyClass = loadWsClass(serviceName);
        Class<?> serviceClass = loadWsClass(serviceName + "Service");

        String getPortMethodName = "get" + substringAfterLast(serviceName, ".") + "Port";
        Method getPortMethod = getExactMethod(serviceClass, getPortMethodName);
        Service service = Reflect.on(serviceClass).create().<Service> get();

        return proxyClass.cast(invoke(getPortMethod, service));
    }

    private static Object createProxy(String serviceName, String module) {
        Class<?> proxyClass = loadWsClass(serviceName);

        JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
        proxyFactory.setAddress(getAddress(module, proxyClass));
        proxyFactory.setServiceClass(proxyClass);

        return proxyClass.cast(proxyFactory.create());
    }

}

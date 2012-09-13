package org.n3r.cxf.utils;

import java.lang.reflect.Method;

import static org.apache.commons.lang3.Validate.*;
import static org.n3r.core.lang.RClass.*;
import static org.n3r.core.lang.RMethod.*;

public class WebServiceCallUtils {

    public static Object doInvoke(Object proxy, String methodName, Object... params) {
        Method proxyMethod = getExactMethod(proxy.getClass(), methodName, getParamTypes(params));
        return invoke(proxyMethod, proxy, params);
    }

    public static Class<?>[] getParamTypes(Object... params) {
        int len = params.length;
        Class<?>[] argTypes = new Class<?>[len];
        for (int i = 0; i < len; i++)
            argTypes[i] = params[i] != null ? params[i].getClass() : null;
        return argTypes;
    }

    public static Class<?> loadWsClass(String className) {
        Class<?> clazz = loadClass(className);
        notNull(clazz, "Class [%s] cannot be loaded.", className);
        return clazz;
    }

}

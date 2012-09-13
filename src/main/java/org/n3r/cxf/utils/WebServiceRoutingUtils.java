package org.n3r.cxf.utils;

import java.io.InputStream;
import java.util.Map;

import org.n3r.core.utils.ConfigUtils;
import org.n3r.core.utils.LineConverter;

import static org.n3r.core.collection.RMap.*;
import static org.n3r.core.utils.RIOUtils.*;
import static org.n3r.cxf.utils.WebServiceAnnotationUtils.*;

public class WebServiceRoutingUtils {

    private static class WebServiceRoute {
        private String module;
        private String url;
    }

    private static Map<String, WebServiceRoute> routeMap = null;

    public static synchronized String getAddress(String module, Class<?> proxyClass) {
        return getBaseUrl(module) + getWsAddress(proxyClass);
    }

    public static synchronized String getBaseUrl(String module) {
        if (routeMap == null) loadConfig("appconfig/WebServiceRouting.conf");
        return routeMap.get(module).url;
    }

    public static synchronized void loadConfig(String configFileName) {
        InputStream inStream = readFileToStream(configFileName);
        loadConfig(inStream);
    }

    private static synchronized void loadConfig(InputStream inStream) {
        routeMap = newHashMap();
        ConfigUtils.loadConfig(inStream, 2, new LineConverter() {
            @Override
            public void convert(String[] fields) {
                WebServiceRoute route = new WebServiceRoute();
                route.module = fields[0].trim();
                route.url = fields[1].trim();
                routeMap.put(route.module, route);
            }
        });
    }

}

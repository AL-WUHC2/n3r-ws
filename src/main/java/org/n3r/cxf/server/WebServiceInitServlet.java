package org.n3r.cxf.server;

import java.util.HashSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.transport.servlet.CXFServlet;

import static org.n3r.core.joor.Reflect.*;
import static org.n3r.cxf.utils.WebServiceAnnotationUtils.*;

public class WebServiceInitServlet extends CXFServlet {

    private static final long serialVersionUID = 5889848611286469002L;

    @Override
    public void loadBus(ServletConfig servletConfig) throws ServletException {
        super.loadBus(servletConfig);
        HashSet<Class<?>> sibClassesSet = getWsImplementationBean("CipWsPackage");
        for (Class<?> clz : sibClassesSet)
            createWebService(clz);
    }

    private void createWebService(Class<?> clazz) {
        JaxWsServerFactoryBean server = new JaxWsServerFactoryBean();
        server.setAddress(getWsAddress(clazz));
        server.setServiceClass(clazz);
        server.setServiceBean(on(clazz).create().get());
        server.create();
    }

}

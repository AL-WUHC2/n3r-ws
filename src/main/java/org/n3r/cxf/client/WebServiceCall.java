package org.n3r.cxf.client;

import static org.apache.commons.lang3.StringUtils.*;
import static org.n3r.cxf.utils.WebServiceCallUtils.*;
import static org.n3r.cxf.utils.WebServiceProxyFactory.*;

public class WebServiceCall {

    protected String module;

    public WebServiceCall() {
        module = null;
    }

    public WebServiceCall(String module) {
        this.module = module;
    }

    public Object call(String target, Object... params) throws Exception {
        String serviceName = substringBeforeLast(target, ".");
        String methodName = substringAfterLast(target, ".");

        Object proxy = createWebServiceProxy(serviceName, module);

        return doInvoke(proxy, methodName, params);
    }

}

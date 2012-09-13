package org.n3r.cxf.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Nullable;
import javax.jws.WebService;

import org.n3r.cxf.annotation.WebServiceAddress;

import com.google.common.base.Predicate;

import static org.n3r.config.Config.*;
import static org.n3r.core.lang.RClass.*;
import static org.n3r.core.lang.RClassPath.*;
import static org.n3r.core.lang.RStr.*;

public class WebServiceAnnotationUtils {

    public static String getWsAddress(Class<?> clz) {
        WebServiceAddress anno = clz.getAnnotation(WebServiceAddress.class);
        return anno == null || anno.value().equals("") ? "/" + clz.getName().replaceAll("\\.", "/") : anno.value();
    }

    /**
     *  Get all Classes with annotation {@link WebService} from configuration packages.
     */
    public static HashSet<Class<?>> getWsImplementationBean(String packageConfigName) {
        List<Class<?>> sibClasses = new ArrayList<Class<?>>();
        String[] configPackages = getStr(packageConfigName, "").split(",");

        for (String wsPackage : configPackages) {
            String pkgName = wsPackage.trim();
            if (isEmpty(pkgName)) continue;

            sibClasses.addAll(getClasses(pkgName, new Predicate<Class<?>>() {
                public boolean apply(@Nullable Class<?> input) {
                    return isConcrete(input) && input.isAnnotationPresent(WebService.class);
                }
            }));
        }

        return new HashSet<Class<?>>(sibClasses);
    }

}

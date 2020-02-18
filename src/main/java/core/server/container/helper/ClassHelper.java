package core.server.container.helper;


import core.server.container.annotation.Controller;
import core.server.container.annotation.Service;
import core.server.utils.ClassUtil;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @Auther: acehcl
 * @Date:
 * @Description:
 */
public class ClassHelper {

    private static final Set<Class<?>> CLASS_SET ;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> beanClassSet = new HashSet<>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }

    private static Collection<? extends Class<?>> getServiceClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Service.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls :CLASS_SET) {
            if (cls.isAnnotationPresent(Controller.class)) {
                classSet.add(cls);
            }

        }
        return classSet;
    }
}
package core.server.container.helper;

import core.server.container.annotation.RequestMapping;
import core.server.container.entity.Handler;
import core.server.entiry.Request;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Auther: acehcl
 * @Date:
 * @Description:
 */
public class ControllerHelper {

    private static final Map<String, Handler> REQUEST_MAP = new HashMap<>();

    static {
        // 获取 controller class set
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtils.isNotEmpty(controllerClassSet)) {
            for (Class<?> controllerClass : controllerClassSet) {

                // 遍历每一个controller class
                Method[] methods = controllerClass.getDeclaredMethods();
                if (ArrayUtils.isNotEmpty(methods)) {
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(RequestMapping.class)) {

                            // 获取controller class 的每一个 有RequestMapping注解的 方法
                            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                            String requestPath = requestMapping.value();
                            String requestMethod = requestMapping.method().name();

                            // 封装请求路径path、请求方法method 保存到 REQUEST_MAP 中
                            Request request = new Request(requestMethod, requestPath);
                            Handler handler = new Handler(controllerClass, method);
                            REQUEST_MAP.put(requestPath, handler);
                        }

                    }
                }

            }
        }
    }

    public static Handler getHandler(String method, String path) {
        return REQUEST_MAP.get(path);
    }
}
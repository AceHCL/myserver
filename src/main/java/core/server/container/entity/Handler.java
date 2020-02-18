package core.server.container.entity;

import java.lang.reflect.Method;

/**
 * @Auther: acehcl
 * @Date:
 * @Description:
 */
public class Handler {

    private Class<?> controllerClass;

    private Method controllerMethod;

    public Handler(Class<?> controllerClass, Method controllerMethod) {
        this.controllerClass = controllerClass;
        this.controllerMethod = controllerMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getControllerMethod() {
        return controllerMethod;
    }
}
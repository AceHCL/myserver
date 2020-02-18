package core.server.container;

import com.alibaba.fastjson.JSON;
import core.server.container.entity.Data;
import core.server.container.entity.Handler;
import core.server.container.helper.BeanHelper;
import core.server.container.helper.ControllerHelper;
import core.server.entiry.Request;
import core.server.entiry.Response;
import core.server.utils.ReflectionUtil;
import lombok.extern.slf4j.Slf4j;


import javax.servlet.annotation.WebServlet;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
/**
 * @Auther: acehcl
 * @Date:
 * @Description:
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
@Slf4j
public class DispatcherServlet implements MyServlet {

    @Override
    public void init() {
        HelpLoader.init();
    }

    @Override
    public void service(Request request, Response response) {
        String method = request.getMethod().name().toUpperCase();
        String path = request.getPath();

        Handler handler = ControllerHelper.getHandler(method, path);
        if (handler != null) {

            // 获取对象实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);

            Method actionMethod = handler.getControllerMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);

            handleDataResult(new Data(result), response);
        }
        log.info("{}not find handler");

    }

    /**
     * 返回json数据
     * @param data 数据
     * @param response response
     */
    @Override
    public void handleDataResult(Data data, Response response) {
        Object model = data.getModel();
        if (model != null) {
            response.setContentType("application/json");
            String json = JSON.toJSON(model).toString();
            response.setBody(json.getBytes(StandardCharsets.UTF_8));
        }
    }
}
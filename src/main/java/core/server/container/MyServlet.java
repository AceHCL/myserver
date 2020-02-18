package core.server.container;

import core.server.container.entity.Data;
import core.server.entiry.Request;
import core.server.entiry.Response;
/**
 * @Auther: acehcl
 * @Date:
 * @Description:
 */
public interface MyServlet {

    /**
     * 初始化方法
     */
    void init();

    /**
     * service 方法
     */
    void service(Request request, Response response);

    /**
     * 处理返回数据的方法
     * @param data 数据
     * @param response response
     */
    void handleDataResult(Data data, Response response);
}

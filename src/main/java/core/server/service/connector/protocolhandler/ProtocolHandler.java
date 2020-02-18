package core.server.service.connector.protocolhandler;

import core.server.lifecycle.AbstractLifecycle;
import core.server.lifecycle.LifecycleState;
import core.server.service.connector.protocolhandler.endpoint.EndPoint;
import core.server.service.connector.protocolhandler.endpoint.NioEndPoint;
import core.server.utils.ConfigConstant;
import core.server.utils.PropsUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

/**
 * @Auther: acehcl
 * @Date:
 * @Description:
 */
@Slf4j
public class ProtocolHandler extends AbstractLifecycle {

    private final static String NIO = "nio";

    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    private EndPoint endPoint;

    @Override
    protected void initInternal() {
        // 获取 port
        int port = 0;
        port = Integer.parseInt(PropsUtil.getString(CONFIG_PROPS, "server.port"));
        if (port == 0) {
            log.info("server.port 非法");
            // 停止服务器

        }

        // 获取连接方式
        String connector = null;
        String conn = null;
        connector = PropsUtil.getString(CONFIG_PROPS, "server.connector");
        conn = NIO;
        if (null == conn) {
            log.info("server.connect 非法");
            // 停止服务器

        }

        assert conn != null;
        switch (conn) {
            case NIO:
                endPoint = new NioEndPoint(port);
                break;
            default:
                endPoint = new NioEndPoint(port);
        }

        endPoint.init();
        log.info("endPoint init with {}, port : {}", connector, port);
    }

    @Override
    protected void startInternal() {
        endPoint.start();
        log.info("endPoint start");
    }

    @Override
    protected void stopInternal() {

    }

    @Override
    public LifecycleState getState() {
        return null;
    }
}
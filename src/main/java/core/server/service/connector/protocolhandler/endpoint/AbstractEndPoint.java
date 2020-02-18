package core.server.service.connector.protocolhandler.endpoint;

import core.server.lifecycle.AbstractLifecycle;

/**
 * @Auther: acehcl
 * @Date: 20-2-17 18:00
 * @Description:
 */
public abstract class AbstractEndPoint extends AbstractLifecycle implements EndPoint{
    protected int port;

    public int getPort() {
        return port;
    }

}
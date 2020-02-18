package core.server.service.connector.protocolhandler.endpoint.poller.wrapper;

import core.server.service.connector.protocolhandler.endpoint.EndPoint;
import core.server.service.connector.protocolhandler.endpoint.poller.Poller;


import java.nio.channels.SocketChannel;

/**
 * @Auther: acehcl
 * @Date:
 * @Description:
 */
public abstract class AbstractWrapper implements Wrapper {

    protected EndPoint server;
    protected SocketChannel client;
    protected Poller poller;

    @Override
    public EndPoint getServer() {
        return server;
    }

    @Override
    public SocketChannel getClient() {
        return client;
    }

    @Override
    public Poller getPoller() {
        return poller;
    }
}
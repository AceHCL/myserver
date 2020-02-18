package core.server.service.connector.protocolhandler.endpoint.poller.wrapper;

import core.server.service.connector.protocolhandler.endpoint.EndPoint;
import core.server.service.connector.protocolhandler.endpoint.poller.Poller;

import java.nio.channels.SocketChannel;

/**
 * @Auther: acehcl
 * @Date:
 * @Description:
 */

public class NioSocketWrapper extends AbstractWrapper{

    public NioSocketWrapper(EndPoint server, SocketChannel client, Poller poller) {
        this.server = server;
        this.client = client;
        this.poller = poller;
    }
}
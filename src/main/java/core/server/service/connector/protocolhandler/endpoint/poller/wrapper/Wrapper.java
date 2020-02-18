package core.server.service.connector.protocolhandler.endpoint.poller.wrapper;

import core.server.service.connector.protocolhandler.endpoint.EndPoint;
import core.server.service.connector.protocolhandler.endpoint.poller.Poller;


import java.nio.channels.SocketChannel;
/**
 * @Auther: acehcl
 * @Date:
 * @Description:
 */
public interface Wrapper {
    SocketChannel getClient();

    Poller getPoller();

    EndPoint getServer();
}

package core.server.service.connector.protocolhandler.endpoint;

import core.server.lifecycle.Lifecycle;
import core.server.service.connector.protocolhandler.endpoint.poller.wrapper.Wrapper;

/**
 * @Auther: acehcl
 * @Date: 20-2-17 18:06
 * @Description:
 */
public interface EndPoint extends Lifecycle {

    void execute(Wrapper attachment);

    int getPort();
}
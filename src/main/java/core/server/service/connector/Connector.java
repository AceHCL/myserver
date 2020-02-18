package core.server.service.connector;

import core.server.lifecycle.AbstractLifecycle;
import core.server.lifecycle.LifecycleState;
import core.server.service.connector.adapter.Adapter;
import core.server.service.connector.protocolhandler.ProtocolHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: acehcl
 * @Date:
 * @Description:
 */
@Slf4j
public class Connector extends AbstractLifecycle {

    private ProtocolHandler protocolHandler;
    private Adapter adapter;

    @Override
    protected void initInternal() {
        this.protocolHandler = new ProtocolHandler();
        protocolHandler.init();
        log.info("protocolHandler init");
    }

    @Override
    protected void startInternal() {
        protocolHandler.start();
        log.info("protocolHandler start");
    }

    @Override
    protected void stopInternal() {
        protocolHandler.stop();
        log.info("protocolHandler stop");
    }

    @Override
    public LifecycleState getState() {
        return null;
    }
}
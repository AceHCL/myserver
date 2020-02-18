package core.server.service;

import core.server.lifecycle.AbstractLifecycle;
import core.server.lifecycle.LifecycleState;
import core.server.service.connector.Connector;
import lombok.extern.slf4j.Slf4j;


/**
 * @Auther: acehcl
 * @Date:
 * @Description:
 */
@Slf4j
public class Service extends AbstractLifecycle {

    private Connector connector;

    @Override
    protected void initInternal() {
        this.connector = new Connector();
        connector.init();
        log.info("connector init");
    }

    @Override
    protected void startInternal() {
        connector.start();

        log.info("connector start");
    }

    @Override
    protected void stopInternal() {
        connector.stop();
        log.info("connector stop");
    }

    @Override
    public LifecycleState getState() {
        return null;
    }
}
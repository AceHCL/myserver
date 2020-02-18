package core.server;

import core.server.lifecycle.AbstractLifecycle;
import core.server.lifecycle.LifecycleState;
import core.server.service.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: acehcl
 * @Date:
 * @Description:
 */
@Slf4j
public class Server extends AbstractLifecycle {

    private Service service;

    protected void initInternal() {
        log.info("server init");
        service = new Service();
        service.init();
    }

    protected void startInternal() {
        log.info("server start");
        service.start();
    }

    protected void stopInternal() {
        log.info("server stop");
        service.stop();
    }

    @Override
    public LifecycleState getState() {
        return null;
    }
}
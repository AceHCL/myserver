package core.server.service.connector.protocolhandler.processor;


import core.server.lifecycle.AbstractLifecycle;
import core.server.service.connector.protocolhandler.endpoint.poller.wrapper.Wrapper;


/**
 * @Auther: acehcl
 * @Date:
 * @Description:
 */
public abstract class AbstractProcessor extends AbstractLifecycle implements Runnable {
    protected Wrapper wrapper;

    public AbstractProcessor(Wrapper wrapper) {
        this.wrapper = wrapper;
    }
}
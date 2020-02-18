package core.server.service.connector.protocolhandler.processor;

import core.server.entiry.Request;
import core.server.entiry.Response;
import core.server.entiry.http.Http11Processor;
import core.server.lifecycle.LifecycleState;
import core.server.service.connector.adapter.Adapter;
import core.server.service.connector.protocolhandler.endpoint.poller.wrapper.NioSocketWrapper;
import core.server.service.connector.protocolhandler.endpoint.poller.wrapper.Wrapper;
import lombok.extern.slf4j.Slf4j;


/**
 * @Auther: acehcl
 * @Date:
 * @Description:
 */
@Slf4j
public class NioProcessor extends AbstractProcessor {

    public NioProcessor(Wrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void run() {
        NioSocketWrapper nioSocketWrapper = (NioSocketWrapper) wrapper;

        log.info("process wrapper");
        // 解析http协议
        Http11Processor httpProcessor = new Http11Processor();
        Request request = httpProcessor.process(nioSocketWrapper);
        Response response = new Response();

        new Adapter(nioSocketWrapper).service(request, response);

    }

    @Override
    protected void initInternal() {

    }

    @Override
    protected void startInternal() {

    }

    @Override
    protected void stopInternal() {

    }

    @Override
    public LifecycleState getState() {
        return null;
    }
}
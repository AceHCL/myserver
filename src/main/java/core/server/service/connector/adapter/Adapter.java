package core.server.service.connector.adapter;

import core.server.container.DispatcherServlet;
import core.server.entiry.Request;
import core.server.entiry.Response;
import core.server.lifecycle.AbstractLifecycle;
import core.server.lifecycle.LifecycleState;
import core.server.service.connector.protocolhandler.endpoint.poller.wrapper.NioSocketWrapper;
import core.server.service.connector.protocolhandler.endpoint.poller.wrapper.Wrapper;
import lombok.extern.slf4j.Slf4j;


import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @Auther: acehcl
 * @Date:
 * @Description:
 */
@Slf4j
public class Adapter extends AbstractLifecycle {

    private Wrapper wrapper;

    public Adapter(NioSocketWrapper nioSocketWrapper) {
        wrapper = nioSocketWrapper;
    }

    public void service(Request request, Response response) {
        // 调用容器
        new DispatcherServlet().service(request, response);
        log.info("get response");
        try {
            ByteBuffer[] buffer = response.getResponseByteBuffer();
            wrapper.getClient().write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("{} process over", wrapper.getClient());
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
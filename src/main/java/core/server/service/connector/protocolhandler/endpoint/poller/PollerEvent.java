package core.server.service.connector.protocolhandler.endpoint.poller;

import core.server.service.connector.protocolhandler.endpoint.poller.wrapper.Wrapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.SelectionKey;
/**
 * @Auther: acehcl
 * @Date:
 * @Description:
 */
@Slf4j
class PollerEvent implements Runnable {

        private Wrapper wrapper;

        public PollerEvent(Wrapper wrapper) {
            this.wrapper = wrapper;
        }

        @Override
        public void run() {
            try {
                if (wrapper.getClient().isOpen()) {
                    wrapper.getClient().configureBlocking(false);
                    wrapper.getClient().register(wrapper.getPoller().getSelector(), SelectionKey.OP_READ, wrapper);
                } else {
                    log.info("client(SocketChannel) 已经关闭");
                }
            } catch (IOException e) {
                log.error("client register IOException");
            }
        }
    }
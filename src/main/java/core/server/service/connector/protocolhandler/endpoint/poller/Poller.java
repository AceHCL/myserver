package core.server.service.connector.protocolhandler.endpoint.poller;

import core.server.lifecycle.LifecycleState;
import core.server.service.connector.protocolhandler.endpoint.EndPoint;
import core.server.service.connector.protocolhandler.endpoint.poller.wrapper.NioSocketWrapper;
import core.server.service.connector.protocolhandler.endpoint.poller.wrapper.Wrapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: acehcl
 * @Date: 20-2-17 20:17
 * @Description:
 */
@Slf4j
public class Poller implements Runnable {

    private EndPoint server;
    private String name;
    private Selector selector;
    private Queue<PollerEvent> events;

    private final Lock LOCK = new ReentrantLock();
    private Condition NOT_EMPTY = LOCK.newCondition();


    public Poller(EndPoint server, String name) {
        this.server = server;
        this.name = name;
        this.events = new ConcurrentLinkedQueue<PollerEvent>();
        try {
            this.selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void register(SocketChannel client) {
        LOCK.lock();
        Wrapper wrapper = new NioSocketWrapper(server, client, this);
        events.offer(new PollerEvent(wrapper));
        NOT_EMPTY.signalAll();
        LOCK.unlock();
        log.info("{} register over", client);
    }

    @Override
    public void run() {

        log.info("Poller[{}] 等待连接", name);

        while (server.getState() == LifecycleState.STARTING_PREP || server.getState() == LifecycleState.STARTED) {
            LOCK.lock();
            try {
                while (events.size() == 0) {
                    NOT_EMPTY.await();
                }
                registerEvent();
                log.info("PollerEvent[{}]注册完毕", name);
                selector.select();
                Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();

                while (selectionKeyIterator.hasNext()) {
                    SelectionKey key = selectionKeyIterator.next();
                    if (key.isReadable()) {
                        Wrapper attachment = (Wrapper) key.attachment();
                        if (attachment != null) {
                            processSocket(attachment);
                        }
                    }
                    selectionKeyIterator.remove();
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            } finally {
                LOCK.unlock();
            }
        }

    }

    private void processSocket(Wrapper attachment) {
        server.execute(attachment);
    }

    private void registerEvent() {
        if (events.size() != 0) {
            for (int i = 0; i < events.size(); i++) {
                events.poll().run();
            }
        }
    }

    public Selector getSelector() {
        return selector;
    }

}
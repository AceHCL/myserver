package core.server.service.connector.protocolhandler.endpoint;

import core.server.lifecycle.LifecycleState;
import core.server.service.connector.protocolhandler.endpoint.poller.Poller;
import core.server.service.connector.protocolhandler.endpoint.poller.wrapper.Wrapper;
import core.server.service.connector.protocolhandler.processor.NioProcessor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @Auther: acehcl
 * @Date: 20-2-17 18:02
 * @Description:
 */
@Slf4j
public class NioEndPoint extends AbstractEndPoint {

    private Acceptor acceptor;
    private int pollerCount = Math.min(2,Runtime.getRuntime().availableProcessors());
    private AtomicInteger pollerRotate = new AtomicInteger(0);
    private Poller[] pollers;
    private Executor executor;
    private ServerSocketChannel serverSocketChannel;

    public NioEndPoint(){
        this(8080);
    }

    public NioEndPoint(int port){
        this.port = port;
    }

    protected void initInternal() {
        executor = new ThreadPoolExecutor(100,
                120,
                1, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(200),
                new ThreadFactory() {
                    int count = 0;
                    public Thread newThread(Runnable runnable) {
                        return new Thread(runnable,"workthread"+count++);
                    }
                },
                new ThreadPoolExecutor.CallerRunsPolicy());

        try {
            this.serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(this.port));
            serverSocketChannel.configureBlocking(true);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("ServerSocketChannel"+"error");
        }

    }

    protected void startInternal() {
        initAcceptor();
        initPoller();
        log.info("init poller and acceptor");
    }
    private void initAcceptor(){
        String acceptorName = "NIOAcceptor";
        acceptor = new Acceptor(acceptorName);
        Thread acceptorThread = new Thread(acceptor,acceptorName);
        acceptorThread.setDaemon(true);
        acceptorThread.start();

    }
    private void initPoller(){
        pollers = new Poller[pollerCount];
        for (int i = 0; i < pollerCount ; i++) {
            String pollerName  = "NIOPoller"+i;
            Poller poller = null;
            poller = new Poller(this,pollerName);

            Thread pollerThread = new Thread(poller, pollerName);
            pollerThread.setDaemon(true);
            pollerThread.start();
            pollers[i] = poller;
        }

    }

    protected void stopInternal() {

    }

    public void execute(Wrapper attachment) {
        executor.execute(new NioProcessor(attachment));

    }
    public void registerToPoller(SocketChannel client){
        Poller poller = getPoller();
        poller.register(client);
        new Thread(poller).start();
    }
    private Poller getPoller(){
        int id = Math.abs(pollerRotate.incrementAndGet()%pollerCount);
        if (pollerRotate.compareAndSet(pollerCount+1,0));
        return pollers[id];
    }


    public LifecycleState getState() {
        return state;
    }

    class Acceptor implements Runnable{
        private String name;

        public Acceptor(String name) {
            this.name = name;
        }

        public void run() {
            log.info("{}[Acceptor:{}]开始监听,端口{}",Thread.currentThread().getName(),name,NioEndPoint.this.getPort());
            while (getState() == LifecycleState.STARTED || getState() == LifecycleState.STARTING_PREP){
                log.info("{}启动完成",this.name);
                SocketChannel client;
                try {
                    client = serverSocketChannel.accept();
                    log.info("Acceptor[{}]接到请求...[{}]", name, client);
                    NioEndPoint.this.registerToPoller(client);
                } catch (IOException e) {
                    log.error("Acceptor[{}] IOException", name);
                }
            }
        }
    }
}
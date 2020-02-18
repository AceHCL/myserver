package core.server.lifecycle;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Auther: acehcl
 * @Date: 20-2-17 17:15
 * @Description:
 */
public abstract class AbstractLifecycle implements Lifecycle {
    
    private final List<LifecycleListener> lifecycleListeners = new CopyOnWriteArrayList<LifecycleListener>();
    public volatile LifecycleState state = LifecycleState.NEW;

    public final synchronized void init() {
        setStateInternal(LifecycleState.INITIALIZING,null);
        initInternal();
        setStateInternal(LifecycleState.INITIALIZED,null);
        
    }
    //模板设计模式
    protected abstract void initInternal();
    
    private void setStateInternal(LifecycleState lifecycleState, Object data){
        this.state = lifecycleState;
        String event = state.getEvent();
        if (event != null){
            fireEvent(event,data);
        }
    }
    private void fireEvent(String event, Object data){
        LifecycleEvent lifecycleEvent  = new LifecycleEvent(this,event,data);
        for (LifecycleListener listener:lifecycleListeners
             ) {
            listener.lifecycleEvent(lifecycleEvent);
        }
    }
 
    public void start() {
        setStateInternal(LifecycleState.STARTING_PREP,null);
        startInternal();
        setStateInternal(LifecycleState.STARTED,null);

    }
    protected abstract void startInternal();

    public void stop() {
        setStateInternal(LifecycleState.STOPPING_PREP, null);
        stopInternal();
        setStateInternal(LifecycleState.STOPPED, null);
    }

    protected abstract void stopInternal();

    public void addLifecycleListener(LifecycleListener listener) {
        this.lifecycleListeners.add(listener);
    }

    public void removeLifecycleListener(LifecycleListener listener) {
        this.lifecycleListeners.remove(listener);
    }
}
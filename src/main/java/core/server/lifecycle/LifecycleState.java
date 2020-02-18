package core.server.lifecycle;
/**
 * @Auther: acehcl
 * @Date:
 * @Description:
 */
public enum LifecycleState {

    NEW(false, null),
    INITIALIZING(false, Lifecycle.BEFORE_INIT_EVENT),
    INITIALIZED(false, Lifecycle.AFTER_INIT_EVENT),
    STARTING_PREP(false, Lifecycle.BEFORE_START_EVENT),
    STARTING(true, Lifecycle.START_EVENT),
    STARTED(true, Lifecycle.AFTER_START_EVENT),
    STOPPING_PREP(true, Lifecycle.BEFORE_STOP_EVENT),
    STOPPING(false, Lifecycle.STOP_EVENT),
    STOPPED(false, Lifecycle.AFTER_STOP_EVENT),
    FAILED(false, null);

    private final boolean state;
    private final String event;

    LifecycleState(boolean state,String event){
        this.state = state;
        this.event = event;
    }

    public String getEvent(){
        return event;
    }

    public boolean getState(){
        return state;
    }
}

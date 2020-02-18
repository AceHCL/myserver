package core.server.lifecycle;

/**
 * @Auther: acehcl
 * @Date: 20-2-17 16:52
 * @Description: 生命周期管理需要的功能
 */
public interface Lifecycle {


    /**
     * The LifecycleEvent type for the "component before init" event.
     */
    String BEFORE_INIT_EVENT = "before_init";


    /**
     * The LifecycleEvent type for the "component after init" event.
     */
    String AFTER_INIT_EVENT = "after_init";


    /**
     * The LifecycleEvent type for the "component start" event.
     */
    String START_EVENT = "start";


    /**
     * The LifecycleEvent type for the "component before start" event.
     */
    String BEFORE_START_EVENT = "before_start";


    /**
     * The LifecycleEvent type for the "component after start" event.
     */
    String AFTER_START_EVENT = "after_start";


    /**
     * The LifecycleEvent type for the "component stop" event.
     */
    String STOP_EVENT = "stop";


    /**
     * The LifecycleEvent type for the "component before stop" event.
     */
    String BEFORE_STOP_EVENT = "before_stop";


    /**
     * The LifecycleEvent type for the "component after stop" event.
     */
    String AFTER_STOP_EVENT = "after_stop";

    LifecycleState getState();
    /**
     * 组件的初始化
     */
    void init();

    /**
     * 启动
     */
    void start();

    /**
     * 停止
     */
    void stop();

    /**
     * 添加监听器
     * @param listener
     */
    void addLifecycleListener(LifecycleListener listener);

    /**
     * 移除监听器
     * @param listener 监听器
     */
    void removeLifecycleListener(LifecycleListener listener);

}
package core.server.lifecycle;

/**
 * @Auther: acehcl
 * @Date: 20-2-17 17:00
 * @Description:
 */
public interface LifecycleListener {
    /**
     * 监听事件
     * @param lifecycleEvent
     */
    void lifecycleEvent(LifecycleEvent  lifecycleEvent);


}
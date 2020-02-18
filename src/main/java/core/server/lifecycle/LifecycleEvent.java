package core.server.lifecycle;

import java.util.EventObject;

/**
 * @Auther: acehcl
 * @Date: 20-2-17 17:02
 * @Description:
 */
public class LifecycleEvent extends EventObject {

    private final Object data;
    private final String type;



    public LifecycleEvent(Lifecycle lifecycle, String type, Object data) {
        super(lifecycle);
        this.data = data;
        this.type = type;
    }

    public Object getData(){
        return data;
    }
    public Lifecycle getLifecycle(){
        return (Lifecycle) getSource();
    }
    public String getType(){
        return type;
    }
}
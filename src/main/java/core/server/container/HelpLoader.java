package core.server.container;

import core.server.container.helper.*;
import core.server.utils.ClassUtil;


/**
 * @Auther: acehcl
 * @Date:
 * @Description:
 */
public class HelpLoader {

    public static void init() {
        Class<?> [] classes = {ClassHelper.class, BeanHelper.class,ControllerHelper.class};
        for (Class<?> cls: classes) {
            ClassUtil.loadClass(cls.getName());
        }
    }
}
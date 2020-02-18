package core.server.container.helper;

import core.server.utils.ConfigConstant;
import core.server.utils.PropsUtil;


import java.util.Properties;

/**
 * @Auther: acehcl
 * @Date:
 * @Description:
 */
public class ConfigHelper {

    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    public static String getAppBasePackage() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_ASSET_PATH);
    }
}
package core.server.utils;

/**
 * @Auther: acehcl
 * @Date:
 * @Description:
 */
public class StringUtil {

    public static String findEqual(String im, String... aim) {
        for (String a : aim) {
            if (a.equalsIgnoreCase(im)) {
                return a;
            }
        }
        return null;
    }

}
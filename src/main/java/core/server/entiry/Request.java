package core.server.entiry;

import core.server.entiry.http.enums.RequestMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Auther: acehcl
 * @Date:
 * @Description:
 */
@Getter
@Setter
@Slf4j
public class Request {

    RequestMethod method;
    private String path;
    private Map<String, List<String>> params;
    private String version;
    private Map<String, List<String>> headers;
    private String url;
    private Cookie[] cookies;

    private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");
    private static final String UTF_8 = "UTF-8";
    private static String NEW_LINE = System.getProperty("line.separator");
    private static String BLOCK = " ";
    private static String PATH_SEPARATOR = File.pathSeparator;


    public Request(byte[] data) {
        try {

            String[] lines = URLDecoder.decode(new String(data, UTF_8_CHARSET), UTF_8).split(NEW_LINE);
            for (int i = 0; i < lines.length; i++) {
                System.out.println(lines[i]);
            }
            // 请求方法、参数、版本号
            parseHeader(lines);
            // ...
            parseHeaders(lines);

            parseUrl();

            parseCookies();

            if (params != null) {
                log.info("method:" + method.name() + ";  param:" + params.toString());
            } else {
                log.info("method:" + method.name() + ";  param is null");
            }

        } catch (UnsupportedEncodingException e) {
            log.error("request decode encodingException");
        }
        log.info("http 读取完毕, 生成request");
    }

    public Request(String method, String path) {
        this.method = RequestMethod.GET;
        this.path = path;
    }

    private void parseCookies() {
        List<String> cookie = headers.get("Cookie");
        if (cookie != null && cookie.size() > 0){
            cookies = new Cookie[cookie.size()];
            for (int i = 0; i < cookie.size(); i++) {
                String[] split = cookie.get(i).split("=");
                cookies[i] = new Cookie(split[0], split[1]);
            }
            headers.remove("Cookie");
        }

    }

    private void parseUrl() {
        url = headers.remove("Host").get(0);
    }

    private void parseHeader(String[] lines) {
        String headerLine = lines[0];
        String[] header = headerLine.split(BLOCK);
        // 提取请求方法
        method = RequestMethod.valueOf(header[0]);

        // 解析uri 提取路径和参数
        String[] split = header[1].split("\\?");
        parsePath(split[0]);
        try {
            parseParams(split[1]);
        } catch (Exception e) {
            params = null;
        }

        // 版本号
        version = header[2].split("/")[1];
    }

    private void parsePath(String pa) {
        path = pa;
    }

    private void parseHeaders(String[] lines) {
        this.headers = new HashMap<>();
        for (int i = 1; i < lines.length; i++) {
            String header = lines[i];
            if ("".equals(header)) {
                break;
            }
            int colonIndex = header.indexOf(':');
            if (colonIndex != -1) {
                String key = header.substring(0, colonIndex);
                String[] values = header.substring(colonIndex + 2).split(",");
                headers.put(key, Arrays.asList(values));
            }
        }
    }

    private void parseParams(String s) {
        String[] paramArr = s.split("&");
        params = new HashMap<>();
        for (String param : paramArr){
            String[] kv = param.split("=");
            String key = kv[0];
            if (!"/".equals(key)) {
                String[] values = kv[1].split(",");
                this.params.put(key, Arrays.asList(values));
            }
        }
    }
}

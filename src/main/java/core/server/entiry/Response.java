package core.server.entiry;

import core.server.entiry.http.Header;
import core.server.entiry.http.enums.HttpStatus;
import lombok.extern.slf4j.Slf4j;


import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: acehcl
 * @Date:
 * @Description:
 */
@Slf4j
public class Response {
    private StringBuilder headerAppender;
    private List<Cookie> cookies;
    private List<Header> headers;
    private HttpStatus status = HttpStatus.OK;
    private byte[] body = null;
    private String contentType = DEFAULT_CONTENT_TYPE;

    private final static String BLANK = " ";
    private final static String CRLF = "\r\n";
    private final static String DEFAULT_CONTENT_TYPE = "application/json";

    public Response() {
        this.headerAppender = new StringBuilder();
        this.cookies = new ArrayList<>();
        this.headers = new ArrayList<>();
    }

    public ByteBuffer[] getResponseByteBuffer() {
        buildResponse();
        byte[] header = this.headerAppender.toString().getBytes(StandardCharsets.UTF_8);
        if (body == null){
            body = "505".getBytes();
        }
        ByteBuffer[] response = {ByteBuffer.wrap(header), ByteBuffer.wrap(body)};
        return response;
    }

    private void buildResponse() {
        buildHeader();
        buildBody();
    }

    private void buildBody() {
        int len = 0;
        if (body != null){
            // Content-Length 值
            len = body.length;
        }
        this.headerAppender.append(len).append(CRLF).append(CRLF);

    }

    private void buildHeader() {
        //HTTP/1.1 200 OK
        headerAppender.append("HTTP/1.1").append(BLANK).append(status.getCode()).append(BLANK).append(status).append(CRLF);
        //Date
        headerAppender.append("Date:").append(BLANK).append(new Date()).append(CRLF);
        // Content-Type
        headerAppender.append("Content-Type:").append(BLANK).append(contentType).append(CRLF);
        // Headers
        if (headers != null) {
            for (Header header : headers) {
                headerAppender.append(header.getKey()).append(":").append(BLANK).append(header.getValue()).append(CRLF);
            }
        }
        // Cookies
        if (cookies.size() > 0) {
            for (Cookie cookie : cookies) {
                headerAppender.append("Set-Cookie:").append(BLANK).append(cookie.getKey()).append("=").append(cookie.getValue()).append(CRLF);
            }
        }
        // Content-Length
        headerAppender.append("Content-Length:").append(BLANK);
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setCookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}

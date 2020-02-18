package core.server.entiry.http;

import core.server.entiry.Request;
import core.server.service.connector.protocolhandler.endpoint.poller.wrapper.Wrapper;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
/**
 * @Auther: acehcl
 * @Date:
 * @Description:
 */
public class Http11Processor {

    public Request process(Wrapper wrapper) {
        Request request = null;
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            ByteArrayOutputStream content = new ByteArrayOutputStream();
            while (wrapper.getClient().read(buffer) > 0) {
                buffer.flip();
                content.write(buffer.array());
            }
            content.close();
            request = new Request(content.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return request;
    }
}
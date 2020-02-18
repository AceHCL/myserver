package core;

import core.server.Server;
import lombok.Data;

import java.util.Scanner;

/**
 * @Auther: acehcl
 * @Date: 20-2-17 15:52
 * @Description:辅助服务的启动类
 */

public class BootStrap implements Runnable{
    private Server server;

    public void run() {
        server = new Server();
        server.init();
        server.start();


        //关闭服务
        Scanner scanner = new Scanner(System.in);
        String order;
        while (scanner.hasNext()){
            order = scanner.next();
            if ("stop".equalsIgnoreCase(order)){
                //停止相关服务
                server.stop();
                System.exit(0);
            }
        }
    }


    public static void main(String[] args) {
        new Thread(new BootStrap()).start();
    }


}
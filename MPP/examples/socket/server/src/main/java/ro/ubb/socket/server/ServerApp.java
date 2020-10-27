package ro.ubb.socket.server;

import ro.ubb.socket.common.HelloService;
import ro.ubb.socket.common.Message;
import ro.ubb.socket.server.service.HelloServiceImpl;
import ro.ubb.socket.server.tcp.TcpServer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by radu.
 */
public class ServerApp {

    public static void main(String[] args) {
        try {
            System.out.println("server started");
            ExecutorService executorService = Executors.newFixedThreadPool(
                    Runtime.getRuntime().availableProcessors()
            );
            HelloService helloService = new HelloServiceImpl(executorService);

            TcpServer tcpServer = new TcpServer(executorService);

            tcpServer.addHandler(HelloService.SAY_HELLO, (request) -> {
                String name = request.getBody();
                Future<String> future = helloService.sayHello(name);
                try {
                    String result = future.get();
                    return new Message("ok", result); //fixme: hardcoded str
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message("error", e.getMessage());//fixme: hardcoded str
                }

            });

//        tcpServer.addHandler(HelloService.SAY_BYE, (request) -> {
//            String name = request.getBody();
//            Future<String> future = helloService.sayBye(name);
//            try {
//                String result = future.get();
//                return new Message("ok", result);
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//                return new Message("error", e.getMessage());
//            }
//        });

            tcpServer.startServer();

            executorService.shutdown();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }

    }
}

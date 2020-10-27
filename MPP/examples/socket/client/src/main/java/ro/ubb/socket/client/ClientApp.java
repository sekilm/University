package ro.ubb.socket.client;

import ro.ubb.socket.client.service.HelloServiceClient;
import ro.ubb.socket.client.tcp.TcpClient;
import ro.ubb.socket.client.ui.Console;
import ro.ubb.socket.common.HelloService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by radu.
 */
public class ClientApp {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        TcpClient tcpClient = new TcpClient();
        HelloService helloService = new HelloServiceClient(executorService, tcpClient);
        Console console = new Console(helloService);
        console.runConsole();

        executorService.shutdown();

        System.out.println("bye client");
    }
}

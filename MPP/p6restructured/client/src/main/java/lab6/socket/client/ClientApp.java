package lab6.socket.client;

import lab6.socket.client.controller.SocketServiceClient;
import lab6.socket.client.tcp.TcpClient;
import lab6.socket.client.ui.Console;
import lab6.socket.common.service.SocketService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientApp {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        TcpClient tcpClient = new TcpClient();
        SocketService socketService = new SocketServiceClient(executorService, tcpClient);
        Console console = new Console(socketService);
        console.runConsole();

        executorService.shutdown();

        System.out.println("bye client");
    }
}

package ro.ubb.socket.client.service;

import ro.ubb.socket.client.tcp.TcpClient;
import ro.ubb.socket.common.HelloService;
import ro.ubb.socket.common.Message;

import java.lang.reflect.Member;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created by radu.
 */
public class HelloServiceClient implements HelloService {
    private ExecutorService executorService;
    private TcpClient tcpClient;

    public HelloServiceClient(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public Future<String> sayHello(String name) {
        return executorService.submit(() -> {
            //create a request
            //send request to server
            //get response

            Message request = new Message(HelloService.SAY_HELLO, name);
            System.out.println("sending request: "+request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: "+response);

            return response.getBody();
        });
    }
}

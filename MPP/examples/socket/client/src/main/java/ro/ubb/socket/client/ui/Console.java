package ro.ubb.socket.client.ui;

import ro.ubb.socket.common.HelloService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by radu.
 */
public class Console {
    private HelloService helloService;

    public Console(HelloService helloService) {
        this.helloService = helloService;
    }

    public void runConsole() {

        String name = "john";
        Future<String> greetingFuture = helloService.sayHello(name); //non-blocking
        try {
            String result = greetingFuture.get(); //blocking :(
            //todo: client side operations should be non-blocking

            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }
}

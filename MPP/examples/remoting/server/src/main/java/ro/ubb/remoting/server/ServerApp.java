package ro.ubb.remoting.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by radu.
 */
public class ServerApp {
    public static void main(String[] args) {
        System.out.println("server starting");

        AnnotationConfigApplicationContext context=
                new AnnotationConfigApplicationContext(
                       "ro.ubb.remoting.server.config"
                );

    }
}

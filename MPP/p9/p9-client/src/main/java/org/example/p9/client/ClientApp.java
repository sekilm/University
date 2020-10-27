package org.example.p9.client;

import org.example.p9.client.ui.Console;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ClientApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "org.example.p9.client.config"
                );

        context.getBean(Console.class).runConsole();
    }
}

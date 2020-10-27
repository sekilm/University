package ro.ubb.springdi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ro.ubb.springdi.repository.StudentRepository;
import ro.ubb.springdi.repository.StudentRepositoryImpl;
import ro.ubb.springdi.service.StudentService;
import ro.ubb.springdi.service.StudentServiceImpl;
import ro.ubb.springdi.ui.Console;

/**
 * Created by radu.
 */
@Configuration
@ComponentScan({"ro.ubb.springdi.ui",
        "ro.ubb.springdi.service",
        "ro.ubb.springdi.repository"})
public class AppConfig {
//    @Bean
//    Console console() {
//        return new Console();
//    }
//
//    @Bean
//    StudentService studentService() {
//        return new StudentServiceImpl();
//    }
//
//    @Bean
//    StudentRepository studentRepository() {
//        return new StudentRepositoryImpl();
//    }
}

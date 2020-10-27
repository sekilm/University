package ro.ubb.remoting.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import ro.ubb.remoting.common.StudentService;
import ro.ubb.remoting.server.service.StudentServiceImpl;

/**
 * Created by radu.
 */
@Configuration
public class ServerConfig {
    @Bean
    RmiServiceExporter rmiServiceExporter() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("StudentService");
        rmiServiceExporter.setServiceInterface(StudentService.class);
        rmiServiceExporter.setService(studentService());
        return rmiServiceExporter;
    }

    @Bean
    StudentService studentService() {
        return new StudentServiceImpl();
    }
}

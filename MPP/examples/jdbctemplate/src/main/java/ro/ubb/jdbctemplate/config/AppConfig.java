package ro.ubb.jdbctemplate.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import ro.ubb.jdbctemplate.repository.StudentRepository;
import ro.ubb.jdbctemplate.repository.StudentRepositoryImpl;

import javax.sql.DataSource;

/**
 * author: radu
 */

@Configuration
public class AppConfig {

    @Bean
    StudentRepository studentRepository() {
        return new StudentRepositoryImpl();
    }


}

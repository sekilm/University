package ro.ubb.springjpa.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"ro.ubb.springjpa.repository", "ro.ubb.springjpa.service", "ro.ubb.springjpa.ui"})
public class CatalogConfig {


}

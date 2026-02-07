package ca.bakuryn.bugtracker.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
    "ca.bakuryn.bugtracker.service",
    "ca.bakuryn.bugtracker.controller",
    "ca.bakuryn.bugtracker.mapper"
})
public class TestWebConfig {

}

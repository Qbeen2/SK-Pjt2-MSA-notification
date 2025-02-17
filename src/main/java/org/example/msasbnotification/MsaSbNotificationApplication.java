package org.example.msasbnotification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.reactive.ReactiveManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {
        ReactiveSecurityAutoConfiguration.class,
        ReactiveManagementWebSecurityAutoConfiguration.class
})
public class MsaSbNotificationApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsaSbNotificationApplication.class, args);
    }
}

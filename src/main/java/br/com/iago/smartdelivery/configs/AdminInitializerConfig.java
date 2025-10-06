package br.com.iago.smartdelivery.configs;

import br.com.iago.smartdelivery.modules.users.CreateUserCase;
import br.com.iago.smartdelivery.modules.users.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminInitializerConfig {

    //@Bean
    public CommandLineRunner initAdmin(CreateUserCase createUserCase) {
        return args -> {
            createUserCase.execute(
                    "admin@smartdelivery.com",
                    "admin123",
                    Role.ADMIN
            );
        };
    }
}

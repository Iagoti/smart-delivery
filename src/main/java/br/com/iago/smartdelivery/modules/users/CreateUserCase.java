package br.com.iago.smartdelivery.modules.users;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserCase {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserCase(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity execute(String userName, String password, Role role){
        this.userRepository.findByUsername(userName).ifPresent(user -> {
            throw new IllegalArgumentException("Usuário já cadastrado.");
        });

        UserEntity user = new UserEntity(
                userName,
                passwordEncoder.encode(password),
                role
        );

        return this.userRepository.save(user);
    }
}

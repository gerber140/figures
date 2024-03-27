package pl.kurs.figures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.kurs.figures.security.entity.Role;
import pl.kurs.figures.security.entity.User;
import pl.kurs.figures.security.repository.UserRepository;

@SpringBootApplication
public class FiguresApplication {

//		implements CommandLineRunner {
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(FiguresApplication.class, args);
	}
//    @Override
//    public void run(String... args) {
//        createAdminIfNotExists();
//    }
//
//    private void createAdminIfNotExists() {
//        if (!userRepository.existsByUsername("admin")) {
//            User admin = User.builder()
//                    .username("admin")
//                    .password(passwordEncoder.encode("admin"))
//                    .role(Role.ADMIN)
//                    .build();
//            userRepository.save(admin);
//        }
//    }
}

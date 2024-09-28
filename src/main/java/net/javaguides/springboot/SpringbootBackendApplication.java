package net.javaguides.springboot;

import net.javaguides.springboot.model.Role;
import net.javaguides.springboot.model.UserEntity;
import net.javaguides.springboot.repository.RoleRepository;
import net.javaguides.springboot.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@SpringBootApplication
public class SpringbootBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringbootBackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) throws Exception {
		return args -> {
			if (!userRepo.existsByUsername("admin")) {
				UserEntity admin = new UserEntity();
				admin.setUsername("admin");
				admin.setPassword(passwordEncoder.encode("admin"));
				Role roles = roleRepo.findByName("ADMIN").get();
				admin.setRoles(Collections.singletonList(roles));
				userRepo.save(admin);
			}
		};
	}
}

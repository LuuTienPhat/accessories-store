package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.service.UserService;
import com.example.demo.storage.StorageProperties;
import com.example.demo.storage.StorageService;


@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties(StorageProperties.class)
public class SpringSecurityJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJwtApplication.class, args);
	}

//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			//storageService.deleteAll();
			//storageService.init();
		};
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
//			userService.saveRole(new Role(null, "ROLE_USER"));
//			userService.saveRole(new Role(null, "ROLE_MANAGER"));
//			userService.saveRole(new Role(null, "ROLE_ADMIN"));
//			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
//			
//			userService.saveUser(new User(null, "John Travolta", "john", "1234",new ArrayList<>(), new ArrayList<>()));
//			userService.saveUser(new User(null, "Will Smith", "will", "1234",new ArrayList<>(), new ArrayList<>()));
//			userService.saveUser(new User(null, "Jim Carry", "jim", "1234",new ArrayList<>(), new ArrayList<>()));
//			userService.saveUser(new User(null, "Arnold", "arnold", "1234",new ArrayList<>(), new ArrayList<>()));

//			userService.addRoleToUser("phat", "ROLE_MANAGER");
//			userService.addRoleToUser("tuan", "ROLE_MANAGER");
//			userService.addRoleToUser("john", "ROLE_ADMIN_WORD");
//			userService.addRoleToUser("kim", "ROLE_ADMIN_SALE");
//			userService.addRoleToUser("guest", "ROLE_CUSTOMER");
//			userService.addRoleToUser("arnold", "ROLE_ADMIN");
//			userService.addRoleToUser("arnold", "ROLE_USER");
		};
	}
}

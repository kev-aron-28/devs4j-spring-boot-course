package com.learning.users;

import com.github.javafaker.Faker;
import com.learning.users.models.entities.Role;
import com.learning.users.models.entities.User;
import com.learning.users.models.entities.UserInRole;
import com.learning.users.repositories.RoleRepository;
import com.learning.users.repositories.UserInRoleRepository;
import com.learning.users.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;

@SpringBootApplication
public class UsersApiApplication implements ApplicationRunner {

	private static final Logger log = LoggerFactory.getLogger(UsersApiApplication.class);
	@Autowired
	private Faker faker;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserInRoleRepository userInRoleRepository;

	public static void main(String[] args) {
		SpringApplication.run(UsersApiApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Role roles[] = {
				new Role("ADMIN"),
				new Role("USER"),
				new Role("SUPPORT")
		};

		for(Role role: roles) {
			roleRepository.save(role);
		}

		for(int i = 0; i < 10; i++) {
			User user = new User();
			user.setUsername(faker.name().username());
			user.setPassword(faker.dragonBall().character());
			User userCreated = userRepository.save(user);
			UserInRole userInRoleCreated = new UserInRole(
					userCreated,
					roles[new Random().nextInt(3)]
			);
			log.info("User created username {} password {} role {}", userCreated.getUsername(), userCreated.getPassword(), userInRoleCreated.getRole().getName());
			userInRoleRepository.save(userInRoleCreated);
		}
	}
}

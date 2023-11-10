package ru.kata.spring.boot_security.demo.dataLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Collections;
import java.util.HashSet;


@Component
public class DataLoader implements CommandLineRunner {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public DataLoader(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) {


        Role roleAdmin = roleService.findByName("ROLE_ADMIN");
        if (roleAdmin == null) {
            roleAdmin = new Role();
            roleAdmin.setName("ROLE_ADMIN");
            roleService.save(roleAdmin);
        }


        Role roleUser = roleService.findByName("ROLE_USER");
        if (roleUser == null) {
            roleUser = new Role();
            roleUser.setName("ROLE_USER");
            roleService.save(roleUser);
        }


        User adminUser = new User();
        adminUser.setFirstname("Vasia");
        adminUser.setLastname("Petrov");
        adminUser.setAge((byte) 30);
        adminUser.setEmail("admin@mail.ru");
        adminUser.setPassword("admin");
        adminUser.setRoles(new HashSet<>(Collections.singletonList(roleAdmin)));
        userService.save(adminUser);


        User regularUser = new User();
        regularUser.setFirstname("Masha");
        regularUser.setLastname("Ivanova");
        regularUser.setAge((byte) 25);
        regularUser.setEmail("user@mail.ru");
        regularUser.setPassword("user");
        regularUser.setRoles(new HashSet<>(Collections.singletonList(roleUser)));
        userService.save(regularUser);

    }
}
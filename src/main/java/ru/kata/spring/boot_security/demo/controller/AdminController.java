package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

//    @PostMapping("/admin")
//    public String createNewUser(@ModelAttribute("user") User user, @RequestParam("roles") List<Long> roleIds) {
//        user.setUsername(user.getEmail());
//        userService.save(user, roleIds);
//        return "redirect:/admin";
//    }


//    @PostMapping("/admin")
//    public String createNewUser(@ModelAttribute("user") User user, @RequestParam("roles") Set<String> roleNames) {
//        user.setUsername(user.getEmail());
//
//        Set<Role> roles = roleNames.stream()
//                .map(roleService::findByName)
//                .collect(Collectors.toSet());
//        userService.save(user, roles);
//        return "redirect:/admin";
//    }


    @PostMapping("/admin")
    public String createNewUser(@ModelAttribute("user") User user, @RequestParam("roles") List<Long> roleIds) {
        user.setUsername(user.getEmail());
        userService.save(user, roleIds);
        return "redirect:/admin";
    }




    @GetMapping("/admin")
    public String getAdminPage(ModelMap model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        List<User> listOfUsers = userService.getAllUsers();
        List<Role> rolesNew=roleService.findAll();
        model.addAttribute("listOfUsers", listOfUsers);
        model.addAttribute("rolesNew", rolesNew);
        model.addAttribute("newUser", new User());
        return "admin";
    }

    @GetMapping("/user/{id}")
    public String getUserPage(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/user/{id}/edit")
    public String getEditPage(Model model, @PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("listRoles", roleService.findAll());
        return "admin";
    }


    @DeleteMapping("/admin/delete/{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }


    @PatchMapping("/admin/edit/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.update(user);
        return "redirect:/admin";
    }

}
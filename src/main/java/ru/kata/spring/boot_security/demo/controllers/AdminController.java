package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Collections;
import java.util.Set;


@Controller
@RequestMapping("/admin")

public class AdminController {
    private final UserService userService;
    private final RoleRepository roleRepository;

    public AdminController(UserService userService,
                           RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }


    @GetMapping()
    public String allUsers(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "admin/admin";
    }

    @GetMapping("/new")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", userService.listRoles());
        return "admin/new";
    }
    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user) throws Exception {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUserForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUser(id)); // добавил
        model.addAttribute("allRoles", userService.listRoles());
        return "admin/edit";
    }
    @PatchMapping ("/{id}/edit")
    public String editUser(@ModelAttribute("user") User user, @PathVariable("id") Long id, Model model) {
        model.addAttribute("allRoles", userService.listRoles());
        userService.updateUser(user);
        return "redirect:/admin";
    }
}



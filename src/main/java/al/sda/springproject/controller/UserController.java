package al.sda.springproject.controller;

import al.sda.springproject.dto.ServiceResponse;
import al.sda.springproject.entity.Role;
import al.sda.springproject.entity.User;
import al.sda.springproject.service.RoleService;
import al.sda.springproject.service.UserDetailsServiceCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class UserController {

    private final UserDetailsServiceCustom userDetailsServiceCustom;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserDetailsServiceCustom userDetailsServiceCustom,
                          RoleService roleService,
                          PasswordEncoder passwordEncoder) {
        this.userDetailsServiceCustom = userDetailsServiceCustom;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String userRegister(ModelMap modelMap) {
        modelMap.addAttribute("user", new User());
        return "register";
    }


    @PostMapping("/register")
    public String userRegister(User user) {
        Role role = new Role();
        role.setRole("VIEWER");
        user.setUserRole(role);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        this.userDetailsServiceCustom.saveNewUser(user);
        return "redirect:/login";
    }

    @GetMapping("/users")
    public String getUsers(ModelMap modelMap){
        modelMap.addAttribute("users",
                this.userDetailsServiceCustom.getAllUsers());
        return "user/users";
    }

    @GetMapping("/user/edit/{username}")
    public String getUserEditForm(@PathVariable String username, ModelMap modelMap) {
        log.info("Inside getUserEditForm with {}", username);
        // Gjej userin me kte username

        ServiceResponse<User> userDetails = this.userDetailsServiceCustom.findUserByUsername(username);
        log.info("Got response from findUserByUsername: {}", userDetails);
        if (userDetails.hasErrors()) {
            throw new RuntimeException("User not found");
        }

        modelMap.addAttribute("user", userDetails.get());
        modelMap.addAttribute("roles", this.roleService.getAllRoles());
        return "user/edit-user";
    }

    @PostMapping("/user/edit/{username}")
    public String updateUser(@PathVariable String username, User user) {
        user.setPassword(
                passwordEncoder.encode(user.getPassword()));
        this.userDetailsServiceCustom.updateUser(user);
        return "redirect:/users";
    }

}

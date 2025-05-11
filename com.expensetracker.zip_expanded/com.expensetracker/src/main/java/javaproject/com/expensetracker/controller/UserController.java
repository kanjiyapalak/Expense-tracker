package javaproject.com.expensetracker.controller;

import javaproject.com.expensetracker.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.access.AccessDeniedException;


import javaproject.com.expensetracker.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javaproject.com.expensetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

//    @GetMapping("/get/{id}")
//    public User getUserById(@PathVariable Long id) {
//        return userService.getUserById(id);
//    }
    
    @GetMapping("/get/{id}")
    public User getUserById(@PathVariable Long id, Authentication authentication) {
        String currentUsername = authentication.getName(); // Get username from authentication
        boolean isAdmin = authentication.getAuthorities().stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        // Fetch the user from DB using the username
        User currentUser = userService.findByUsername(currentUsername);

        // Now compare the actual user ID instead of the username
        if (!isAdmin && !currentUser.getId().equals(id)) {
            throw new AccessDeniedException("You are not allowed to access this user's data.");
        }

        return userService.getUserById(id);
    } 

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userService.updateUser(id, updatedUser);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}

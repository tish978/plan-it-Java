/*
package com.example.planit.controller;

import com.example.planit.model.User;
import com.example.planit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AppController {

    @Autowired
    private UserRepository userRepository;

    /*
    @PostMapping("/sign-up")
    public String handleSignUp(@RequestParam String username, @RequestParam String password) {
        User existingUser = userRepository.findByUsernameAndPassword(username, password);
        if (existingUser != null) {
            return "sign-up-error";
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        userRepository.save(newUser);
        return "redirect:/home";
    }

    */

/*

    @PostMapping("/sign-up")
    @ResponseBody
    public String handleSignUp(@RequestParam String username, @RequestParam String password) {
        User existingUser = userRepository.findByUsernameAndPassword(username, password);
        if (existingUser != null) {
            return "Sign-up error: User already exists.";
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        userRepository.save(newUser);
        return "Sign-up successful!";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(HttpServletRequest request, @RequestParam String username, @RequestParam String password) {
        User existingUser = userRepository.findByUsernameAndPassword(username, password);
        if (existingUser != null) {
            request.getSession().setAttribute("currUserID", existingUser.getId());
            System.out.println("The _id value of the curr_userID is: " + existingUser.getId());
            return "Successful login!";
        } else {
            System.out.println("ERROR: Username or Password is not correct.");
            return "ERROR: Username or Password is not correct.";
        }
    }
}
*/

package com.example.planit.controller;

import com.example.planit.model.User;
import com.example.planit.repository.UserRepository;
import com.example.planit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

@Controller
public class AppController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    @ResponseBody
    public CompletableFuture<String> handleSignUp(@RequestParam String username, @RequestParam String password) {
        return userRepository.findByUsernameAndPassword(username, password)
                .thenCompose(existingUser -> {
                    if (existingUser != null) {
                        return CompletableFuture.completedFuture("Sign-up error: User already exists.");
                    }
                    User newUser = new User();
                    newUser.setUsername(username);
                    newUser.setPassword(password);
                    return userService.saveUser(newUser)
                            .thenApply(savedUser -> "Sign-up successful!");
                });
    }

    @PostMapping("/login")
    @ResponseBody
    public CompletableFuture<String> login(HttpServletRequest request, @RequestParam String username, @RequestParam String password) {
        return userRepository.findByUsernameAndPassword(username, password)
                .thenApply(existingUser -> {
                    if (existingUser != null) {
                        request.getSession().setAttribute("currUserID", existingUser.getId());
                        System.out.println("The _id value of the curr_userID is: " + existingUser.getId());
                        return "Successful login!";
                    } else {
                        System.out.println("ERROR: Username or Password is not correct.");
                        return "ERROR: Username or Password is not correct.";
                    }
                });
    }
}




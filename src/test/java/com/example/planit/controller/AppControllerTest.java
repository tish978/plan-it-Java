/*
package com.example.planit.controller;

import com.example.planit.model.User;
import com.example.planit.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
public class AppControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AppController appController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(appController).build();
    }

    @Test
    public void testHandleSignUp_UserExists() throws Exception {
        User existingUser = new User();
        existingUser.setUsername("testuser");
        existingUser.setPassword("password");

        when(userRepository.findByUsernameAndPassword("testuser", "password")).thenReturn(existingUser);

        mockMvc.perform(post("/sign-up")
                        .param("username", "testuser")
                        .param("password", "password"))
                .andExpect(status().isOk())
                .andExpect(view().name("sign-up-error"));
    }

    @Test
    public void testHandleSignUp_NewUser() throws Exception {
        when(userRepository.findByUsernameAndPassword("newuser", "newpassword")).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(new User());

        mockMvc.perform(post("/sign-up")
                        .param("username", "newuser")
                        .param("password", "newpassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/home"));
    }
}

*/

package com.example.planit.controller;

import com.example.planit.model.User;
import com.example.planit.repository.UserRepository;
import com.example.planit.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class AppControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private AppController appController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(appController).build();
    }

    @Test
    public void testHandleSignUp_UserExists() throws Exception {
        User existingUser = new User();
        existingUser.setUsername("testuser");
        existingUser.setPassword("password");

        when(userRepository.findByUsernameAndPassword("testuser", "password"))
                .thenReturn(CompletableFuture.completedFuture(existingUser));

        MvcResult mvcResult = mockMvc.perform(post("/sign-up")
                        .param("username", "testuser")
                        .param("password", "password"))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().string("Sign-up error: User already exists."));
    }

    @Test
    public void testHandleSignUp_NewUser() throws Exception {
        when(userRepository.findByUsernameAndPassword("newuser", "newpassword"))
                .thenReturn(CompletableFuture.completedFuture(null));
        when(userService.saveUser(any(User.class)))
                .thenReturn(CompletableFuture.completedFuture(new User()));

        MvcResult mvcResult = mockMvc.perform(post("/sign-up")
                        .param("username", "newuser")
                        .param("password", "newpassword"))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().string("Sign-up successful!"));
    }

    @Test
    public void testLogin_Success() throws Exception {
        User existingUser = new User();
        existingUser.setUsername("testuser");
        existingUser.setPassword("password");

        when(userRepository.findByUsernameAndPassword("testuser", "password"))
                .thenReturn(CompletableFuture.completedFuture(existingUser));

        MvcResult mvcResult = mockMvc.perform(post("/login")
                        .param("username", "testuser")
                        .param("password", "password"))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().string("Successful login!"));
    }

    @Test
    public void testLogin_Failure() throws Exception {
        when(userRepository.findByUsernameAndPassword("wronguser", "wrongpassword"))
                .thenReturn(CompletableFuture.completedFuture(null));

        MvcResult mvcResult = mockMvc.perform(post("/login")
                        .param("username", "wronguser")
                        .param("password", "wrongpassword"))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().string("ERROR: Username or Password is not correct."));
    }
}

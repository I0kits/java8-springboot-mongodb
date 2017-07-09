package com.huawei.live.controller;

import com.huawei.live.entity.User;
import com.huawei.live.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoRestController {
    private UserRepository userRepository;

    @Autowired
    public DemoRestController(
            UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users/by_name")
    public Object greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        List<User> users = userRepository.findByName(name);
        return users;
    }
}

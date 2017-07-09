package com.huawei.live.controller;

import com.huawei.live.entity.User;
import com.huawei.live.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity add(@RequestBody User user) {
        userService.add(user);
        return ResponseEntity.ok(userService.getAll().size());
    }

    @GetMapping
    public ResponseEntity get() {
        return ResponseEntity.ok(userService.getAll());
    }

    @DeleteMapping
    public ResponseEntity deleteAll(){
        userService.deleteAll();
        return ResponseEntity.ok().build();
    }
}

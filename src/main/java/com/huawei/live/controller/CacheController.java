package com.huawei.live.controller;

import com.huawei.live.entity.Person;
import com.huawei.live.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {
    @Autowired
    private PersonService demoService;

    //http://localhost:8080/put?id=1&name=abel&age=23&address=shanghai
    @GetMapping("/put")
    public Person put(Person person) {
        return demoService.save(person);

    }

    //http://localhost:8080/able?id=1
    @GetMapping("/able")
    public Person cacheable(Person person) {
        return demoService.findOne(person);
    }

    //http://localhost:8080/evit?id=1
    @GetMapping("/evit")
    public String evit(Long id) {
        demoService.remove(id);
        return "ok";

    }
}

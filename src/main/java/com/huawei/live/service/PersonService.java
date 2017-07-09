package com.huawei.live.service;

import com.huawei.live.entity.Person;

public interface PersonService {

    Person save(Person person);

    void remove(Long id);

    Person findOne(Person person);
}
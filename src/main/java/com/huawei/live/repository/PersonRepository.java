package com.huawei.live.repository;

import com.huawei.live.entity.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, Long> {

}

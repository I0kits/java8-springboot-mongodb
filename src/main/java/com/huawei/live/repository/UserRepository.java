package com.huawei.live.repository;

import com.huawei.live.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, Long> {
    List<User> findByName(String username);
}

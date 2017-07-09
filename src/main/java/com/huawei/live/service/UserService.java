package com.huawei.live.service;

import com.huawei.live.entity.User;

import java.util.List;

public interface UserService {
    void add(User user);

    List<User> getAll();

    void deleteAll();
}

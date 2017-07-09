package com.huawei.live.service.Impl;

import com.huawei.live.entity.Person;
import com.huawei.live.repository.PersonRepository;
import com.huawei.live.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Override
    //@CachePut缓存新增的或更新的数据到缓存,其中缓存名字是 people 。数据的key是person的id
    @CachePut(value = "people", key = "#person.id")
    public Person save(Person person) {
        Person p = personRepository.save(person);
        System.out.println("为id、key为:"+p.getId()+"数据做了缓存");
        return p;
    }

    @Override
    //@CacheEvict 从缓存people中删除key为id 的数据
    @CacheEvict(value = "people")
    public void remove(Long id) {
        System.out.println("删除了id、key为"+id+"的数据缓存");

    }

    @Override
    //@Cacheable缓存key为person 的id 数据到缓存people 中,如果没有指定key则方法参数作为key保存到缓存中。
    @Cacheable(value = "people", key = "#person.id")
    public Person findOne(Person person) {
        Person p = personRepository.findOne(person.getId());
        System.out.println("为id、key为:"+p.getId()+"数据做了缓存");
        return p;
    }
}

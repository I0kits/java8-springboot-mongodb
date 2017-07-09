package com.huawei.live;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableCaching
public class HwLiveApplication {
	public static void main(String[] args) {
		SpringApplication.run(HwLiveApplication.class, args);
	}
}

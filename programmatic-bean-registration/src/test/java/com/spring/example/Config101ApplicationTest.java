package com.spring.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = Config101Application.class)
class Config101ApplicationTest {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(this.applicationContext.getBean(FooService.class));
        Assertions.assertNotNull(this.applicationContext.getBean(BarService.class));
    }
}
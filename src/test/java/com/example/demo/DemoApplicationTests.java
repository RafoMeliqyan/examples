package com.example.demo;

import com.example.demo.mappers.UserMapper;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class DemoApplicationTests {

    @Autowired
    private UserService service;

    @Test
    void contextLoads() {
    }

    final Logger logger = LoggerFactory.getLogger(DemoApplicationTests.class);

    @Test
    public void get() {
        User user1 = service.save("poxos","poxos@mail.com");
        User user2 = service.save("petros","petros@mail.com");

        getAndPrint(user1.getId());
        getAndPrint(user2.getId());
        getAndPrint(user1.getId());
        getAndPrint(user2.getId());
    }

    @Test
    public void create() {
        createAndPrint("Valod","Valod@mail.ru");
        createAndPrint("Valod","Valod1122@mail.ru");
        createAndPrint("Petik","Valod@mail.ru");

        logger.info("all entries are below:");
        service.getAll().forEach(u -> logger.info("{}", u.toString()));
    }

    @Test
    public void createAndRefresh() {
        User user1 = service.createOrReturnCached(new User("Hovsep", "vasya@mail.ru"));
        logger.info("created user1: {}", user1);

        User user2 = service.createOrReturnCached(new User("Hovsep", "misha@mail.ru"));
        logger.info("created user2: {}", user2);

        User user3 = service.createAndRefreshCache(new User("Hovsep", "kolya@mail.ru"));
        logger.info("created user3: {}", user3);

        User user4 = service.createOrReturnCached(new User("Hovsep", "petik@mail.ru"));
        logger.info("created user4: {}", user4);
    }

    @Test
    public void delete() {
        User user1 = service.save("Vasya", "vasya@mail.ru");
        logger.info("{}", service.getUser(user1.getId()));

        User user2 = service.save("VALOD", "valod@mail.ru");
        logger.info("{}", service.getUser(user2.getId()));

        service.delete(user1.getId());
        service.deleteAndEvict(user2.getId());

        logger.info("{}", service.getUser(user1.getId()));
        logger.info("{}", service.getUser(user2.getId()));
    }


    @Test
    public void saveTest() {
        User user = new User(19,"Valodik3", "Valodik3@mail.com");
        service.save(user.getName(), user.getEmail());
        logger.info("{}", service.getUser(user.getId()));
        UserMapper.INSTANCE.toDto(user);
    }

    @Test
    public void getTest() {
        User user = service.getUser(17);
        UserMapper.INSTANCE.toDto(user);
    }

    private void createAndPrint(String name, String email) {
        logger.info("created user: {}", service.save(name, email));
    }

    private void getAndPrint(int id) {
        logger.info("user found: {}", service.getUser(id));
    }
    
}

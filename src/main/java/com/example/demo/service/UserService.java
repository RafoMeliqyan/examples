package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.util.List;

@Service
@Slf4j
public class UserService {

    final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Cacheable("users")
    public User getUser(int id) {
        logger.info("getting user by id: {}", id);
        return userRepository.findUserById(id);
    }

    @Cacheable(value = "users", key = "#name")
    public User save(String name, String email) {
        logger.info("creating user with parameters: {}, {}", name, email);
        return userRepository.save(new User(name,email));
    }

    public void updateUser(int id, User user) {
        User byId = userRepository.getOne(id);

        byId.setName(user.getName());
        byId.setEmail(user.getEmail());
        byId.setProduct(user.getProduct());

        userRepository.save(byId);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Cacheable(value = "users", key = "#user.name")
    public User createOrReturnCached(User user) {
        logger.info("creating user: {}", user);
        return userRepository.save(user);
    }

    @CachePut(value = "users", key = "#user.name")
    public User createAndRefreshCache(User user) {
        logger.info("creating user: {}", user);
        return userRepository.save(user);
    }

    public void delete(int id) {
        logger.info("deleting user by id: {}", id);
        userRepository.deleteById(id);
    }

    @CacheEvict("users")
    public void deleteAndEvict(int id) {
        logger.info("deleting user by id: {}", id);
        userRepository.deleteById(id);
    }

}

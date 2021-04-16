package com.example.demo.endpoint;

import com.example.demo.dto.UserDto;
import com.example.demo.mappers.UserMapper;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/allUsers")
    public List<User> allUsers() {
        return userService.getAll();
    }

    @GetMapping(value = "/user/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
                                      produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto getUser(@PathVariable("id") int id) {
        User user = userService.getUser(id);
        return UserMapper.INSTANCE.toDto(user);
    }

    @PostMapping("/save")
    public User save(@RequestBody User user) {
        return userService.save(user.getName(),user.getEmail());
    }

    @DeleteMapping("/user/delete/{id}")
    public void deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
    }

    @PutMapping("/user/update/{id}")
    public void updateUser(@PathVariable("id") int id, @RequestBody User user) {
        userService.updateUser(id, user);
    }

}

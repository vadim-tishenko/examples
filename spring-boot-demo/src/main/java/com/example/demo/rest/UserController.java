package com.example.demo.rest;

import com.example.demo.data.User;
import com.example.demo.data.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by vadim.tishenko
 * on 05.06.2018 21:01.
 */
@RestController
public class UserController {
    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository=repository;
        User u=new User();
        u.setName("Ivan");
        repository.save(u);
    }

    @GetMapping("/users")
    public List<UserDto> get(){
        return Collections.singletonList(new UserDto(){{
            id=1;
            name="Vad";
        }});
    }

    @GetMapping("/users2")
    public List<UserDto> get2(){
        List<UserDto> res = repository.findAll().stream()
                .map(user -> new UserDto() {{
                    id=user.getId();
                    name=user.getName();
                }}).collect(Collectors.toList());
        return res;
    }

}

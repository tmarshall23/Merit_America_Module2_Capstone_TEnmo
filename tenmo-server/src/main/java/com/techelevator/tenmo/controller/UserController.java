package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    JdbcUserDao dao;

    public UserController(JdbcUserDao dao) {
        this.dao = dao;
    }

    @GetMapping("")
    public List<String> getUsers(@RequestParam String username){
        return dao.findAllUsername(username);

    }





}

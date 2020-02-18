package test.controller;

import core.server.container.annotation.Controller;
import core.server.container.annotation.RequestMapping;
import test.entity.User;

/**
 * @Auther: acehcl
 * @Date: 20-2-18 15:01
 * @Description:
 */
@Controller
public class Login {

    @RequestMapping("/login")
    public User login(){
        User user = new User();
        user.setPassword("root");
        user.setUsername("root");
        System.out.println("login success");
        return user;
    }

}
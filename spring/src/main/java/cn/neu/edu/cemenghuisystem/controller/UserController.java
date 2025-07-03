package cn.neu.edu.cemenghuisystem.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @PostMapping("/test")
    @ResponseBody
    public String test(Authentication authentication) {
        return authentication.getName();
    }

}

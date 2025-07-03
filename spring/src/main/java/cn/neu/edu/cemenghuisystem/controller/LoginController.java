package cn.neu.edu.cemenghuisystem.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("")
public class LoginController {
      @PostMapping("/login")
      @ResponseBody
    public String login(@RequestParam String username, @RequestParam String password) {
          return "login";
      }
}

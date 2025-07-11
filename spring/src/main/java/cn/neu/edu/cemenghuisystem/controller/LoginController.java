package cn.neu.edu.cemenghuisystem.controller;

import cn.neu.edu.cemenghuisystem.pojo.BasicResponse;
import cn.neu.edu.cemenghuisystem.pojo.LoginResponse;
import cn.neu.edu.cemenghuisystem.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("public/")
public class LoginController {

    @Autowired
    private LoginService loginService;

      @PostMapping("login")
      @ResponseBody
    public LoginResponse login(@RequestParam String account, @RequestParam String password) {
          return loginService.login(account,password);
      }

      @PostMapping("/register")
    public BasicResponse register(@RequestParam String account, @RequestParam String password,String companyName,String companyContact) {
     return null;
      }

}

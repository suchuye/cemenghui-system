package cn.neu.edu.cemenghuisystem.controller;

import cn.neu.edu.cemenghuisystem.pojo.BasicResponse;
import cn.neu.edu.cemenghuisystem.pojo.LoginResponse;
import cn.neu.edu.cemenghuisystem.pojo.UserProfileResponse;
import cn.neu.edu.cemenghuisystem.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/public")
public class LoginController {

    @Autowired
    private LoginService loginService;

      @PostMapping("/login")
      @ResponseBody
    public LoginResponse login(@RequestParam String account, @RequestParam String password) {
          return loginService.login(account,password);
      }

      @PostMapping("/register")
      @ResponseBody
    public BasicResponse register(@RequestParam String account, @RequestParam String password,String companyName,String companyContact) {
     return loginService.register(account,password,companyName,companyContact);
      }




    @GetMapping("/user/profile")
    @ResponseBody
    public UserProfileResponse getProfile(Authentication authentication) {
        return loginService.getUserProfile(authentication);
    }

    // 更新个人信息
    @PutMapping("/user/profile")
    public Map<String, Object> updateProfile(@RequestHeader("Authorization") String token, @RequestBody Map<String, Object> request) {
        return null;
    }

    // 修改密码
    @PutMapping("/user/password")
    public Map<String, Object> changePassword(@RequestHeader("Authorization") String token, @RequestBody Map<String, Object> request) {
        return null;
    }

    // 用户管理 - 获取用户列表
    @GetMapping("/users")
    public Map<String, Object> getUserList(@RequestHeader("Authorization") String token,
                                           @RequestParam(required = false) String account,
                                           @RequestParam(required = false) String companyName,
                                           @RequestParam(required = false) String nickname,
                                           @RequestParam(required = false) String phone,
                                           @RequestParam(required = false) String status,
                                           @RequestParam(required = false, defaultValue = "1") Integer page,
                                           @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return null;
    }

    // 用户管理 - 创建用户
    @PostMapping("/users")
    public Map<String, Object> createUser(@RequestHeader("Authorization") String token, @RequestBody Map<String, Object> request) {
        return null;
    }

    // 用户管理 - 更新用户信息
    @PutMapping("/users/{id}")
    public Map<String, Object> updateUser(@RequestHeader("Authorization") String token, @PathVariable String id, @RequestBody Map<String, Object> request) {
        return null;
    }

    // 用户管理 - 删除用户
    @DeleteMapping("/users/{id}")
    public Map<String, Object> deleteUser(@RequestHeader("Authorization") String token, @PathVariable String id) {
        return null;
    }

    // 用户管理 - 分配用户角色
    @PutMapping("/users/{id}/roles")
    public Map<String, Object> assignUserRole(@RequestHeader("Authorization") String token, @PathVariable String id, @RequestBody Map<String, Object> request) {
        return null;
    }

    // 角色与权限管理 - 获取角色列表
    @GetMapping("/roles")
    public Map<String, Object> getRoleList(@RequestHeader("Authorization") String token) {
        return null;
    }

    // 角色与权限管理 - 获取权限列表
    @GetMapping("/permissions")
    public Map<String, Object> getPermissionList(@RequestHeader("Authorization") String token) {
        return null;
    }

    // 角色与权限管理 - 分配角色权限
    @PutMapping("/roles/{id}/permissions")
    public Map<String, Object> assignRolePermissions(@RequestHeader("Authorization") String token, @PathVariable String id, @RequestBody Map<String, Object> request) {
        return null;
    }

}

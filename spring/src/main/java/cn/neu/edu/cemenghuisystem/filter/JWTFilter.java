package cn.neu.edu.cemenghuisystem.filter;

import cn.neu.edu.cemenghuisystem.pojo.User;
import cn.neu.edu.cemenghuisystem.service.UserService;
import cn.neu.edu.cemenghuisystem.utils.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JWTFilter extends OncePerRequestFilter {
    //TODO 错误处理，超时

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {


        String jwtToken = null;
        final String requestTokenHeader = request.getHeader("Authorization");

        // 从请求头中获取JWT令牌
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7); // 去掉 "Bearer " 前缀
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        // 如果 JWT 令牌不为空且当前请求未被认证
        if (jwtToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            int id= jwtUtil.extractId(jwtToken);
            User user=userService.getUserById(id);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            user.getId(), null, null);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 设置认证信息到安全上下文
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 继续过滤器链的执行
        chain.doFilter(request, response);
    }
}

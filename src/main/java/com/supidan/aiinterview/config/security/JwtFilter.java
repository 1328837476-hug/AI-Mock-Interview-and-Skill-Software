package com.supidan.aiinterview.config.security;

import com.supidan.aiinterview.config.CustomUserDetails;
import com.supidan.aiinterview.entity.po.User;
import com.supidan.aiinterview.mapper.UserMapper;
import com.supidan.aiinterview.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil    jwtUtil;
    private final UserMapper userMapper;

    // 构造方法注入，@Lazy 打破循环依赖
    public JwtFilter(JwtUtil jwtUtil, @Lazy UserMapper userMapper) {
        this.jwtUtil    = jwtUtil;
        this.userMapper = userMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {


        String authHeader = request.getHeader("Authorization");

        // 没有Token直接放行
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            if (jwtUtil.validateToken(token)) {
                Claims claims = jwtUtil.parseToken(token);
                Long   userId = claims.get("userId", Long.class);
                User   user   = userMapper.selectById(userId);

                if (user != null && user.getStatus() == 1) {
                    CustomUserDetails userDetails = new CustomUserDetails(user);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null,
                                    userDetails.getAuthorities());
                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request));
                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            log.warn("Token解析失败: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

}
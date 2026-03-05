package com.supidan.aiinterview.config;

import com.supidan.aiinterview.config.security.JwtFilter;
import com.supidan.aiinterview.domain.po.User;
import com.supidan.aiinterview.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(@Lazy JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/ws/**").permitAll()
                        // 放行 Knife4j / Swagger 相关路径
                        .requestMatchers(
                                "/doc.html",
                                "/webjars/**",
                                "/swagger-resources/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req, res, e) -> {
                            res.setContentType("application/json;charset=UTF-8");
                            res.setStatus(200);
                            res.getWriter().write(
                                    "{\"code\":401,\"message\":\"未登录或Token失效\",\"data\":null}");
                        })
                )
                .addFilterBefore(jwtFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 提供 UserDetailsService Bean
     * 阻止 Spring Security 自动配置生成随机密码
     */
    @Bean
    public UserDetailsService userDetailsService(UserMapper userMapper) {
        return username -> {
            User user = userMapper.selectByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException(
                        "用户不存在: " + username);
            }
            return new CustomUserDetails(user);
        };
    }
}
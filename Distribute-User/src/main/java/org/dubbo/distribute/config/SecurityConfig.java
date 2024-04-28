package org.dubbo.distribute.config;

import org.dubbo.distribute.constant.SecurityConstants;
import org.dubbo.distribute.exception.JwtAccessDeniedHandler;
import org.dubbo.distribute.exception.JwtAuthenticationEntryPoint;
import org.dubbo.distribute.filter.JwtAuthorizationFilter;
import org.dubbo.distribute.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static java.util.Collections.singletonList;

/**
 * @description: Spring Security 配置类
 * @author: muqingfeng
 * @date: 2024/4/23 23:51
 */

@EnableWebSecurity
@Configuration
public class SecurityConfig {


    @Autowired
    private RedisUtils redisUtils;

    /**
     * 密码编码器
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //基于token 所以不需要CSRF防护
        http.cors(Customizer.withDefaults())
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(SecurityConstants.SWAGGER_WHITELIST).permitAll()
                .antMatchers(SecurityConstants.H2_CONSOLE).permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.SYSTEM_WHITELIST).permitAll()
                // 其他的接口都需要认证后才能请求Controller
                .anyRequest().authenticated()
                .and()
                // 对于usernamePasswordAuthenticationFilter 过滤器而言，请求必须符合 post, /login 才会进入这个过滤器
                //因此这里直接使用 addFilterAt() 方法 ，让自定义的过滤器替换掉 usernamePasswordAuthenticationFilter
                //添加自定义的 filter 到 过滤器链中的 UsernamePasswordAuthenticationFilter 前面
                //UsernamePasswordAuthenticationFilter 是 authentication 开始的 filter
                .addFilterAt(new JwtAuthorizationFilter(redisUtils), UsernamePasswordAuthenticationFilter.class)
                //基于token  不需要session(不创建会话)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                //授权异常处理
                .exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                .accessDeniedHandler(new JwtAccessDeniedHandler());

        return http.build();
    }


    /**
     * Cors 配置优化
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        org.springframework.web.cors.CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(singletonList("*"));
        // configuration.setAllowedOriginPatterns(singletonList("*"));
        configuration.setAllowedHeaders(singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "OPTIONS"));
        configuration.setExposedHeaders(singletonList(SecurityConstants.TOKEN_HEADER));
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

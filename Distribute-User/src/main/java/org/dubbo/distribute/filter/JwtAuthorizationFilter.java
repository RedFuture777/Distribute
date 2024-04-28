package org.dubbo.distribute.filter;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.dubbo.distribute.constant.SecurityConstants;
import org.dubbo.distribute.util.JwtTokenUtils;
import org.dubbo.distribute.util.RedisUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器处理所有HTTP请求，并检查是否存在带有正确令牌的Authorization标头。例如，如果令牌未过期或签名密钥正确。
 */
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final RedisUtils redisUtils;

    public JwtAuthorizationFilter( RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        //获取并验证请求头中的token是否符合要求
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);
        if (token == null || !token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            //如果不符合，显示的清楚当前线程的上下文的值
            SecurityContextHolder.clearContext();
            chain.doFilter(request, response);
            return;
        }
        //获取请求头中的 token 内容
        String tokenValue = token.replace(SecurityConstants.TOKEN_PREFIX, "");
        // 验证 token 是否有效
        UsernamePasswordAuthenticationToken authentication = null;
        try {
            //解析出token中的id,并验证redis中是不是存在对应这个id 的token
            String redisToken = redisUtils.get(JwtTokenUtils.getId(tokenValue));
            if(redisToken == null || !redisToken.equals(tokenValue)){
                //如果不符合，显示的清楚当前线程的上下文的值
                SecurityContextHolder.clearContext();
                chain.doFilter(request, response);
                return;
            }
            authentication = JwtTokenUtils.getAuthentication(tokenValue);
        }catch (JwtException e){
            logger.error("Invalid jwt : " + e.getMessage());
        }
        //将认证过了的凭证（即权限）保存到security 的上下文中
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }


}

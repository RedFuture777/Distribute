package org.dubbo.distribute.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.dubbo.distribute.constant.SecurityConstants;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: jwt 工具
 * @author: muqingfeng
 * @date: 2024/4/23 22:42
 */
public class JwtTokenUtils {
    /**
     * 生成足够的安全随机密钥，以适合符合规范的签名
     */
    private static final byte[] API_KEY_SECRET_BYTES = DatatypeConverter.parseBase64Binary(SecurityConstants.JWT_SECRET_KEY);

    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(API_KEY_SECRET_BYTES);


    /**
     * 创建用户登陆后的身份令牌
     *
     * @param username     用户名称
     * @param id           用户ID
     * @param role         用户角色
     * @param isRememberMe 是否记住我
     * @return 返回令牌 token 字符串
     */
    public static String createToken(String username, String id, List<String> role, boolean isRememberMe) {
        long expiration = isRememberMe ? SecurityConstants.EXPIRATION_REMEMBER : SecurityConstants.EXPIRATION;
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expiration * 1000);
        String tokenSuffix = Jwts.builder()
                .setHeaderParam("type", SecurityConstants.TOKEN_TYPE)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .claim(SecurityConstants.ROLE_CLAIMS, String.join(",", role))
                .setId(id)
                .setIssuer("muqingfeng")
                .setIssuedAt(createdDate)
                .setSubject(username)
                .setExpiration(expirationDate)
                .compact();
        return SecurityConstants.TOKEN_PREFIX + tokenSuffix;
    }

    /**
     *
     * @param token 令牌字符串
     * @return 获取 Claims 声明
     */
    private static Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJwt(token)
                .getBody();
    }


    /**
     *
     * @param token 令牌字符串
     * @return 获取用户ID
     */
    public static String getId(String token){
        Claims claims = getClaims(token);
        return claims.getId();
    }

    /**
     * 从claims对象中获取角色信息，并将其转换为SimpleGrantedAuthority类型的列表
     * @param claims
     * @return
     */
    private static List<SimpleGrantedAuthority> getAuthorities(Claims claims){
        String role = (String) claims.get(SecurityConstants.ROLE_CLAIMS);
        return Arrays.stream(role.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /**
     * 从输入的token中解析出用户信息和权限，然后将它们封装在UsernamePasswordAuthenticationToken对象中，以便在后续的权限验证中使用。
     */
    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Claims claims = getClaims(token);
        List<SimpleGrantedAuthority> authorities = getAuthorities(claims);
        String userName = claims.getSubject();
        //使用三个参数的构造函数，将认证状态设置为true
        return new UsernamePasswordAuthenticationToken(userName, token, authorities);
    }




}

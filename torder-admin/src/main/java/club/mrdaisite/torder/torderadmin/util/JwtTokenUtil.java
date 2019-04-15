package club.mrdaisite.torder.torderadmin.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtTokenUtil
 *
 * @author dai
 * @date 2019/03/22
 */
@Component
public class JwtTokenUtil {
    public static String token = null;
    private static final String CLAIM_KEY_USERNAME = "sub";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;
    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * 根据负责生成JWT的token
     *
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(key)
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .compact();
    }

    /**
     * 从token中获取JWT中的负载
     *
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 生成token的过期时间
     *
     * @return
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 从token中获取登录用户名
     *
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            LoggerUtil.logger.warn(token);
            Claims claims = getClaimsFromToken(token);
            LoggerUtil.logger.warn(claims.getId());
            LoggerUtil.logger.warn(claims.getSubject());
            LoggerUtil.logger.warn(claims.getIssuedAt().toString());
            LoggerUtil.logger.warn(claims.getIssuer());
            LoggerUtil.logger.warn(claims.getExpiration().toString());
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 从token中获取登录用户ID
     *
     * @param token token
     * @return 登录用户ID
     */
    public Long getUserIdFromToken(String token) {
        Long id;
        try {
            Claims claims = getClaimsFromToken(token);
            id = Long.parseLong(claims.getId());
        } catch (Exception e) {
            id = null;
        }
        return id;
    }

    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     * @param userDetails 从数据库中查询出来的用户信息
     */
    public boolean vallidateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否已经失效
     *
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     *
     * @param token
     * @return
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 根据用户信息生成token
     *
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        return generateToken(claims);
    }

    /**
     * 判断token是否可以被刷新
     *
     * @param token
     * @return
     */
    public boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     *
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return generateToken(claims);
    }
}

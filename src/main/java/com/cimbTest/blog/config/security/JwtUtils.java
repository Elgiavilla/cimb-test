package com.cimbTest.blog.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

@Component
@Slf4j
public class JwtUtils {

  @Value("${cimb.app.jwtSecret}")
  private String jwtSecret;

  @Value("${cimb.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  public String generateToken(Authentication authentication)
      throws UnsupportedEncodingException {

    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
    Date now = new Date();

    Claims claims = Jwts.claims().setSubject(userPrincipal.getId().toString());
    claims.put("id", userPrincipal.getId());
    claims.put("username", userPrincipal.getUsername());
    return Jwts.builder()
        .setSubject(userPrincipal.getId().toString())
        .setIssuedAt(now)
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .setClaims(claims)
        .signWith(key(), SignatureAlgorithm.HS256)
        .compact();
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key()).build()
        .parseClaimsJws(token).getBody().getSubject();
  }

  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
      return true;
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }

//  public String generateTokenFromUsername(String username) {
//    return Jwts.builder()
//        .setSubject(username)
//        .setIssuedAt(new Date())
//        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
//        .signWith(key(), SignatureAlgorithm.HS256)
//        .compact();
//  }
}

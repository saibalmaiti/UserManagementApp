package com.cts.UserManagementApp.filter;

import com.cts.UserManagementApp.service.UserService;
import com.cts.UserManagementApp.service.UserServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
@Component
public class JwtFilter extends GenericFilterBean {
    private String privateKey = "my secret key";
    @Autowired
    private UserService userService;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRq = (HttpServletRequest) servletRequest;
        HttpServletResponse httpRe = (HttpServletResponse) servletResponse;

        String authHeader = httpRq.getHeader("authorization");
//        log.info(privateKey);
        if(authHeader == null || !authHeader.startsWith("Bearer"))
            throw new ServletException("Missing or invalid authorization header");
        String jwtToken = authHeader.substring(7);
        Claims claims = Jwts.parser().setSigningKey(privateKey).parseClaimsJws(jwtToken).getBody();
        log.info(claims.get("jti").toString());
        if(userService.getExpiredToken(claims.get("jti").toString()) != null)
            throw new ServletException("Token is invalid");
        httpRq.setAttribute("username",claims);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}

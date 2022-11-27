package com.cts.UserManagementApp.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
public class JwtFilter extends GenericFilterBean {
    private String privateKey = "my secret key";
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
        httpRq.setAttribute("username",claims);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}

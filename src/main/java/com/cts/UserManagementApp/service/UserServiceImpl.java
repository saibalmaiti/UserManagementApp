package com.cts.UserManagementApp.service;

import com.cts.UserManagementApp.model.User;
import com.cts.UserManagementApp.model.UserDto;
import com.cts.UserManagementApp.repository.UserRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Value("${jwt.secret}")
    private String secretKey;
    @Override
    public boolean addUser(User user) {
        Optional<User> userOptional = userRepo.findById(user.getEmpId());
        if (userOptional.isPresent()) {
            return false;
        }
        userRepo.save(user);
        return true;
    }

    @Override
    public boolean validateUser(UserDto userDto) {
        User user = userRepo.validateUser(userDto.getEmpName(), userDto.getPassword());
        return user != null;
    }
    @Override
    public User getUserById(long empId) {
        return userRepo.findById(empId).get();
    }

    @Override
    public String generateToken(UserDto userDto) throws  ServletException{

        String jwtToken = "";
        if(userDto.getEmpName() == null || userDto.getPassword() == null)
            throw new ServletException("Please Enter a valid username and password");
        if(!validateUser(userDto))
            throw new ServletException("Invalid Credentials");
        else {
            log.info(secretKey);
            jwtToken = Jwts.builder().setSubject(userDto.getEmpName()).setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis()+5000000))
                    .signWith(SignatureAlgorithm.HS256,secretKey).compact();
        }
        return jwtToken;
    }
}
